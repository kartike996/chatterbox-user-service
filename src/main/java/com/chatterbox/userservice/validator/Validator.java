package com.chatterbox.userservice.validator;

import com.chatterbox.userservice.exception.MandatoryFieldException;
import com.chatterbox.userservice.exception.UserAlreadyExistsException;
import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

/**
 * Utility class for validating user-related input data in the ChatterBox application.
 *
 * This class contains static methods that:
 * - Ensure required user fields (firstName, userName, email, id) are not blank
 * - Check for duplicate user entries based on userName or email
 *
 * Throws custom exceptions such as:
 * - MandatoryFieldException: when a required field is missing or empty
 * - UserAlreadyExistsException: when a user with the same userName or email already exists
 *
 * Intended to be used by service-layer components to enforce data integrity before persisting user data.
 */
@Component
public class Validator {

    public void validateMandatoryFields(User user) {
        if (Strings.isBlank(user.getFirstName())) {
            throw new MandatoryFieldException("The field firstName is mandatory and cannot be null or empty.");
        }
        validateUserName(user.getUserName());
        if (Strings.isBlank(user.getEmail())) {
            throw new MandatoryFieldException("The field email is mandatory and cannot be null or empty.");
        }
    }

    public void validateUserName(String userName) {
        if (Strings.isBlank(userName)) {
            throw new MandatoryFieldException("The field 1 is mandatory and cannot be null or empty.");
        }
    }

    public void validateUserId(String id) {
        if(Strings.isBlank(id)) {
            throw new MandatoryFieldException("The field id is mandatory and cannot be null or empty.");
        }
    }

    public void validateUserUniquenessForRegistration(User user, UserRepository userRepository) {
        // Check for duplicate username
        userRepository.findByUserName(user.getUserName()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(user.getId())) {
                throw new UserAlreadyExistsException("Username " + user.getUserName() + " already exists.");
            }
        });

        // Check for duplicate email
        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(user.getId())) {
                throw new UserAlreadyExistsException("Email " + user.getEmail() + " already exists.");
            }
        });
    }

    public void validateUserUniquenessOnUpdate(User user, UserRepository userRepository) {
        // Check for duplicate username (only if used by another user)
        userRepository.findByUserName(user.getUserName()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(user.getId())) {
                throw new UserAlreadyExistsException("Username " + user.getUserName() + " already exists.");
            }
        });

        // Check for duplicate email (only if used by another user)
        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(user.getId())) {
                throw new UserAlreadyExistsException("Email " + user.getEmail() + " already exists.");
            }
        });
    }
}
