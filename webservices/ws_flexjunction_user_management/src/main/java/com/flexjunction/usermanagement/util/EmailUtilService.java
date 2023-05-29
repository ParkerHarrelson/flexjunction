package com.flexjunction.usermanagement.util;

import com.flexjunction.usermanagement.dao.EmailSender;
import com.flexjunction.usermanagement.entity.User;
import com.flexjunction.usermanagement.exception.ConfirmationEmailFailureException;
import com.flexjunction.usermanagement.repository.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static com.flexjunction.usermanagement.constants.ExceptionConstants.EMAIL_EXCEPTION;

@Slf4j
@Service
@AllArgsConstructor
public class EmailUtilService {

    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public void sendEmail(String email, String token, String subject, String message, String endpoint) {
        Email from = new Email(emailSender.getFromEmail());
        Email to = new Email(email);
        Content emailContent = new Content("text/plain", message + endpoint + token);
        Mail mail = new Mail(from, subject, to, emailContent);

        SendGrid sg = new SendGrid(emailSender.getApiKey());
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info(String.valueOf(response.getStatusCode()));
        }  catch (IOException e) {
            throw new ConfirmationEmailFailureException(String.format(EMAIL_EXCEPTION, email));
        }
    }

    public boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isAvailableEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmailAndExpirationTimestampIsNull(email);
        return existingUser.isEmpty();
    }

}
