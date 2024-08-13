package com.fastshop.e_commerce.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fastshop.e_commerce.exceptions.StandardError;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(BadCredentialsException.class)
        private ResponseEntity<StandardError> badCredentials(BadCredentialsException ex, HttpServletRequest request) {
                StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException ex,
                        HttpServletRequest request) {
                StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(error);
        }
}
