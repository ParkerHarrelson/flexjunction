package com.flexjunction.usermanagement.exceptionhandler;

import com.flexjunction.usermanagement.controller.UserManagementController;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.exception.InvalidPasswordException;
import com.flexjunction.usermanagement.exception.ResetTokenException;
import com.flexjunction.usermanagement.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.flexjunction.usermanagement.constants.RegistrationStatus.FAILED;


@Slf4j
@ControllerAdvice(assignableTypes = UserManagementController.class)
public class ManagementExceptionsHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<String> handleInvalidUsername(UserNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResetTokenException.class)
    protected ResponseEntity<String> handleResetToken(ResetTokenException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
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
