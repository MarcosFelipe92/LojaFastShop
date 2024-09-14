package com.fastshop.e_commerce.exceptions.handlers;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fastshop.e_commerce.exceptions.StandardError;
import com.fastshop.e_commerce.exceptions.service.DatabaseException;
import com.fastshop.e_commerce.exceptions.service.InvalidEmailException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    private ResponseEntity<StandardError> database(DatabaseException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidEmailException.class)
    private ResponseEntity<StandardError> invalidEmail(InvalidEmailException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException ex,
            HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(IOException.class)
    private ResponseEntity<StandardError> io(IOException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
