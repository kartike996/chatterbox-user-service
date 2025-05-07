package com.chatterbox.userservice.service;

import com.chatterbox.userservice.exception.UserDoesNotExistException;
import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;
import com.chatterbox.userservice.validator.UserServiceValidator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService is a service class responsible for handling the business logic related to user operations.
 * It provides methods for registering, updating, retrieving, and deleting users, as well as validating
 * user input and ensuring data consistency during operations.
 *
 * Key Methods:
 * - `registerUser(User user)`: Registers a new user after validating the mandatory fields and checking for uniqueness.
 * - `updateUser(User user)`: Updates an existing user by validating mandatory fields and ensuring no conflicts with other users.
 * - `getUserById(String id)`: Retrieves a user by their unique ID, throwing an exception if the user is not found.
 * - `getUserByUserName(String userName)`: Retrieves a user by their username, ensuring the username is checked in a case-insensitive manner.
 * - `getAll()`: Retrieves a list of all users.
 * - `deleteUser(String id)`: Deletes a user by their ID if found, or logs that the user does not exist.
 * - `deleteAll()`: Deletes all users from the database.
 *
 * This service utilizes a repository to interact with the database and a validator to ensure that user data
 * adheres to the required constraints and business rules. It handles errors such as non-existent users and
 * conflicts like duplicate usernames or emails.
 */
@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    
    private UserRepository userRepository;
    private UserServiceValidator validator;

    public String registerUser(User user) {
        validator.validateMandatoryFields(user);
        validator.validateUserUniquenessForRegistration(user, userRepository);

    	userRepository.save(user);
        log.info("user with id {} is registered", user.getId());
        return String.format("User registered with id %s, userName %s, firstName %s, lastName %s, and email %s",
                user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public String updateUser(User user) {
        validator.validateMandatoryFields(user);
        validator.validateUserUniquenessOnUpdate(user, userRepository);

        userRepository.save(user);
        log.info("User details updated for id: {}", user.getId());
        return "User details updated";
    }

    public User getUserById(String id) {
        validator.validateUserId(id);
        return userRepository.findById(id).orElseThrow(() ->
                new UserDoesNotExistException("User with ID " + id + " not found"));
    }

    public User getUserByUserName(String userName) {
        validator.validateUserName(userName);
        return userRepository.findByUserName(userName.toLowerCase()).orElseThrow(() ->
                new UserDoesNotExistException("User with userName " + userName + " not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String deleteUser(String id) {
        userRepository.findById(id).ifPresent(user -> userRepository.delete(user));
        log.info("User with id {} is deleted or does not exist", id);
        return "User with id " + id + " is deleted or does not exist";
    }

    public String deleteAll() {
        userRepository.deleteAll();
        return "All users deleted";
    }
}