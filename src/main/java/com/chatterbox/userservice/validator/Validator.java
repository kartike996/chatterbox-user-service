package com.chatterbox.userservice.validator;

import com.chatterbox.userservice.exception.MandatoryFieldException;
import com.chatterbox.userservice.exception.UserAlreadyExistsException;
import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

/**
 * Validator is a component class responsible for validating various aspects of the User entity.
 * It performs checks for mandatory fields, user uniqueness, and proper formatting of user data.
 * The validations are used during user registration and update processes to ensure data integrity
 * and prevent errors such as missing or duplicate values.
 *
 * Key Methods:
 * - `validateMandatoryFields(User user)`: Validates that all mandatory fields (e.g., first name, email, username) are provided and not empty.
 * - `validateUserName(String userName)`: Validates that the username is not blank.
 * - `validateUserId(String id)`: Validates that the user ID is not blank.
 * - `validateUserUniquenessForRegistration(User user, UserRepository userRepository)`: Checks if the username or email already exists in the repository during user registration.
 * - `validateUserUniquenessOnUpdate(User user, UserRepository userRepository)`: Checks if the username or email is already used by another user during user update.
 *
 * The class ensures that all necessary fields are present and that there are no duplicate usernames or emails,
 * throwing appropriate exceptions when validation fails (e.g., `MandatoryFieldException`, `UserAlreadyExistsException`).
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
            if (!existingUser.getId().equalsIgnoreCase(user.getId())) {
                throw new UserAlreadyExistsException("Username " + user.getUserName() + " already exists.");
            }
        });

        // Check for duplicate email
        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getId().equalsIgnoreCase(user.getId())) {
                throw new UserAlreadyExistsException("Email " + user.getEmail() + " already exists.");
            }
        });
    }

    public void validateUserUniquenessOnUpdate(User user, UserRepository userRepository) {
        // Check for duplicate username (only if used by another user)
        userRepository.findByUserName(user.getUserName()).ifPresent(existingUser -> {
            if (!existingUser.getId().equalsIgnoreCase(user.getId())) {
                throw new UserAlreadyExistsException("Username " + user.getUserName() + " already exists.");
            }
        });

        // Check for duplicate email (only if used by another user)
        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getId().equalsIgnoreCase(user.getId())) {
                throw new UserAlreadyExistsException("Email " + user.getEmail() + " already exists.");
            }
        });
    }
}
