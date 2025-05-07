package com.chatterbox.userservice.exception;

/**
 * UserDoesNotExistException is a custom runtime exception used to signal that a
 * requested user could not be found in the system.
 *
 * This exception is commonly thrown when operations such as fetch, update, or delete
 * are attempted on a user ID or identifier that does not correspond to any existing record.
 *
 * It helps ensure clear and consistent handling of "not found" scenarios, and is mapped
 * to a 404 Not Found HTTP response by the GlobalExceptionHandler.
 *
 * Example usage:
 * userRepository.findById(id)
 *     .orElseThrow(() -> new UserDoesNotExistException("User with ID " + id + " not found"));
 */
public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
