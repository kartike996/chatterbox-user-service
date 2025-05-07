package com.chatterbox.userservice.exception.handler;

import com.chatterbox.userservice.exception.MandatoryFieldException;
import com.chatterbox.userservice.exception.UserAlreadyExistsException;
import com.chatterbox.userservice.exception.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler provides centralized exception handling across all controllers.
 *
 * Annotated with @RestControllerAdvice, this class intercepts exceptions thrown during the
 * execution of REST API requests and returns standardized, user-friendly error responses.
 *
 * Handled Exceptions:
 * - MethodArgumentNotValidException: Captures and returns validation errors for request payloads.
 * - UserAlreadyExistsException: Thrown when attempting to register a user with duplicate username/email.
 * - MandatoryFieldException: Triggered when required user input fields are missing.
 * - UserDoesNotExistException: Raised when the specified user is not found in the system.
 * - Exception: Catch-all handler for any unanticipated runtime errors.
 *
 * Each handler returns a consistent error structure with HTTP status, timestamp, error type,
 * and a descriptive message to aid client-side debugging and user feedback.
 *
 * Example response:
 * {
 *   "timestamp": "2025-05-06T10:45:00",
 *   "status": 400,
 *   "error": "Validation Error",
 *   "message": {
 *     "userName": "Username is required",
 *     "email": "Email format is invalid"
 *   }
 * }
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Error");

        // Collect validation errors
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing // in case of duplicate keys
                ));

        body.put("message", fieldErrors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Handle UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT); // 409 Conflict
    }

    // Handle MandatoryFieldException
    @ExceptionHandler(MandatoryFieldException.class)
    public ResponseEntity<Map<String, Object>> handleMandatoryField(MandatoryFieldException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());  // 400 Bad Request
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserDoesNotExistException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Optional: catch-all for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred.");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
}
