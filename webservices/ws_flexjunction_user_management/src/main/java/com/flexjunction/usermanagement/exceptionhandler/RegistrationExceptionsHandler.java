package com.flexjunction.usermanagement.exceptionhandler;

import com.flexjunction.usermanagement.controller.UserRegistrationController;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.exception.InvalidPasswordException;
import com.flexjunction.usermanagement.exception.InvalidUsernameException;
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
}
