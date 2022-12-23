package com.bsu.znatoki.controller.handler;

import com.bsu.znatoki.exception.NotFoundEntityException;
import com.bsu.znatoki.exception.UserAlreadyExistsException;
import com.bsu.znatoki.exception.UserNotActivatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;

@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {
    @ExceptionHandler(value = AccessDeniedException.class)
    protected @ResponseBody ResponseEntity<String> handleAccessDeniedException(
            RuntimeException exc, WebRequest request) {
        log.warn("AccessDeniedException handled! Response status: " + HttpStatus.FORBIDDEN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exc.getMessage());
    }

    @ExceptionHandler(value = UserNotActivatedException.class)
    protected @ResponseBody ResponseEntity<String> handleUserNotActivatedException(
            RuntimeException exc, WebRequest request) {
        log.warn("UserNotActivatedException exception handled! Response status: " + HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected @ResponseBody ResponseEntity<String> handleUserAlreadyExistsException(
            RuntimeException exc, WebRequest request) {
        log.warn("UserAlreadyExistsException exception handled! Response status: " + HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(value = MessagingException.class)
    protected @ResponseBody ResponseEntity<String> handleMessagingException(
            RuntimeException exc, WebRequest request) {
        log.warn("MessagingException exception handled! Response status: " + HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(value = NotFoundEntityException.class)
    protected @ResponseBody ResponseEntity<String> handleUserNotFoundException(
            RuntimeException exc, WebRequest request) {
        log.warn("NotFoundEntityException exception handled! Response status: " + HttpStatus.NOT_FOUND);
        return ResponseEntity.notFound().build();
    }
}
