package com.flexjunction.usermanagement.service;

import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
public class RateLimitingService {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static final Duration EXPIRE_TIME = Duration.ofMinutes(1);

    private final Map<String, ConcurrentLinkedDeque<Long>> requestTimestamps = new ConcurrentHashMap<>();
    private final ValueOperations<String, String> valueOperations;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public RateLimitingService(StringRedisTemplate stringRedisTemplate) {
        this.valueOperations = stringRedisTemplate.opsForValue();
        executorService.submit(this::cleanupOldClients);
    }

    public boolean isAllowed(HttpServletRequest request) {
        String clientId = getClientId(request);
        try {
            String value = valueOperations.get(clientId);

            if (value == null) {
                valueOperations.set(clientId, "1", EXPIRE_TIME);
                return true;
            } else {
                int requests = Integer.parseInt(value);
                if (requests >= MAX_REQUESTS_PER_MINUTE) {
                    return false;
                } else {
                    valueOperations.increment(clientId);
                    return true;
                }
            }
        } catch (DataAccessException e) {
            log.error("Redis failed, falling back to in-memory rate limiting", e);
            ConcurrentLinkedDeque<Long> timestamps = requestTimestamps.computeIfAbsent(clientId, k -> new ConcurrentLinkedDeque<>());

            long cutoff = System.currentTimeMillis() - EXPIRE_TIME.toMillis();
            while (!timestamps.isEmpty() && timestamps.peekFirst() < cutoff) {
                timestamps.pollFirst();
            }

            if (timestamps.size() >= MAX_REQUESTS_PER_MINUTE) {
                return false;
            } else {
                timestamps.addLast(System.currentTimeMillis());
                return true;
            }
        }
    }

    private String getClientId(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((ip + "|" + userAgent).getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to hash client ID", e);
            return ip + "|" + userAgent;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void cleanupOldClients() {
        while (true) {
            try {
                Thread.sleep(EXPIRE_TIME.toMillis());
                long cutoff = System.currentTimeMillis() - EXPIRE_TIME.toMillis();
                requestTimestamps.entrySet().removeIf(entry -> {
                    ConcurrentLinkedDeque<Long> deque = entry.getValue();
                    while (!deque.isEmpty() && deque.peekFirst() < cutoff) {
                        deque.pollFirst();
                    }
                    return deque.isEmpty();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @PreDestroy
    public void preDestroy() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}
