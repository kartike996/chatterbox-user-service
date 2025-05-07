package com.chatterbox.userservice.exception;

/**
 * MandatoryFieldException is a custom runtime exception used to indicate that
 * one or more required fields in the user input are missing or null.
 *
 * This exception is typically thrown during request validation to enforce
 * business rules that mandate the presence of certain fields (e.g., username, email).
 *
 * It is handled centrally by the GlobalExceptionHandler to return a
 * standardized 400 Bad Request response with an appropriate error message.
 *
 * Example usage:
 * if (user.getUserName() == null) {
 *     throw new MandatoryFieldException("Username is required.");
 * }
 */
public class MandatoryFieldException extends RuntimeException {
    public MandatoryFieldException(String message) {
        super(message);
    }
}
