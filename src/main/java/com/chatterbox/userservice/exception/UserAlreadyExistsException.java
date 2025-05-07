package com.chatterbox.userservice.exception;

/**
 * UserAlreadyExistsException is a custom runtime exception used to indicate that
 * a user with the same unique attribute (such as username or email) already exists
 * in the system.
 *
 * This exception is typically thrown during user registration or update operations
 * when a duplicate constraint is violated, ensuring data uniqueness and integrity.
 *
 * It is handled centrally by the GlobalExceptionHandler to return a standardized
 * 409 Conflict response along with a descriptive error message.
 *
 * Example usage:
 * if (userRepository.findByEmail(user.getEmail()).isPresent()) {
 *     throw new UserAlreadyExistsException("Email already in use.");
 * }
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
