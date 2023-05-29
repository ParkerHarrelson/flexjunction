package com.flexjunction.usermanagement.exceptionhandler;

import com.flexjunction.usermanagement.controller.UserRegistrationController;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.flexjunction.usermanagement.constants.RegistrationStatus.FAILED;

@Slf4j
@ControllerAdvice(assignableTypes = UserRegistrationController.class)
public class RegistrationExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidUsernameException.class)
    protected ResponseEntity<UserRegistrationStatusDTO> handleInvalidUsername(InvalidUsernameException e) {
        log.error(e.getMessage(), e);
        UserRegistrationStatusDTO status = UserRegistrationStatusDTO.builder()
                .username(e.getUsername())
                .status(FAILED.getStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(status, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    protected ResponseEntity<UserRegistrationStatusDTO> handleInvalidEmail(InvalidEmailException e) {
        log.error(e.getMessage(), e);
        UserRegistrationStatusDTO status = UserRegistrationStatusDTO.builder()
                .username(e.getUsername())
                .status(FAILED.getStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(status, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    protected ResponseEntity<UserRegistrationStatusDTO> handleInvalidPassword(InvalidPasswordException e) {
        log.error(e.getMessage(), e);
        UserRegistrationStatusDTO status = UserRegistrationStatusDTO.builder()
                .username(e.getUsername())
                .status(FAILED.getStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidAddressException.class)
    protected ResponseEntity<UserRegistrationStatusDTO> handleInvalidAddress(InvalidAddressException e) {
        log.error(e.getMessage(), e);
        UserRegistrationStatusDTO status = UserRegistrationStatusDTO.builder()
                .username(e.getUsername())
                .status(FAILED.getStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingNameException.class)
    protected ResponseEntity<UserRegistrationStatusDTO> handleMissingName(MissingNameException e) {
        log.error(e.getMessage(), e);
        UserRegistrationStatusDTO status = UserRegistrationStatusDTO.builder()
                .username(e.getUsername())
                .status(FAILED.getStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingSecurityQuestionException.class)
    protected ResponseEntity<UserRegistrationStatusDTO> handleMissingSecurityQuestion(MissingSecurityQuestionException e) {
        log.error(e.getMessage(), e);
        UserRegistrationStatusDTO status = UserRegistrationStatusDTO.builder()
                .username(e.getUsername())
                .status(FAILED.getStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConfirmationEmailFailureException.class)
    protected ResponseEntity<String> handleFailedEmail(ConfirmationEmailFailureException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ConfirmAccountException.class)
    protected ResponseEntity<String> handleFailedConfirmAccount(ConfirmAccountException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
