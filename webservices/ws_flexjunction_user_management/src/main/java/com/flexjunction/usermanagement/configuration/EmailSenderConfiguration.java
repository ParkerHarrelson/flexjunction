package com.flexjunction.usermanagement.configuration;

import com.flexjunction.usermanagement.dao.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailSenderConfiguration {

    @Bean
    public EmailSender emailSender(@Value("${flex-junction.confirmation-email.email}") String email,
                                   @Value("${EMAIL_API}") String apiKey) {
        return new EmailSender(email, apiKey);
    }
}
