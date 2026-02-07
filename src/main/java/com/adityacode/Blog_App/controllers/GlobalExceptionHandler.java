package com.adityacode.Blog_App.controllers;

import com.adityacode.Blog_App.domain.dtos.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.error("Unhandled exception occurred: ", e);
        ApiErrorResponse error = ApiErrorResponse.builder()
                .StatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .Message("Internal Server Error")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("IllegalArgumentException: {}", e.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .StatusCode(HttpStatus.BAD_REQUEST.value())
                .Message(e.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException e) {
        log.warn("IllegalStateException: {}", e.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .StatusCode(HttpStatus.CONFLICT.value())
                .Message(e.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialException(BadCredentialsException e) {
        log.warn("Bad credentials: {}", e.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .StatusCode(HttpStatus.UNAUTHORIZED.value())
                .Message("Bad Credentials")
                .build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("Not Found: {}", e.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .StatusCode(HttpStatus.NOT_FOUND.value())
                .Message("Bad Credentials")
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
