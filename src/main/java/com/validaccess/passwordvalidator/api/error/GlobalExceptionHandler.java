package com.validaccess.passwordvalidator.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Turns malformed request bodies (e.g. invalid JSON) into a small, explicit
 * error payload instead of the framework's default whitelabel/error body.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMalformedRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("Request body is missing or malformed. Expected JSON: {\"password\": \"...\"}"));
    }
}
