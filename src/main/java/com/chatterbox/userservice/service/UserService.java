package com.chatterbox.userservice.service;

import com.chatterbox.userservice.exception.UserDoesNotExistException;
import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;
import com.chatterbox.userservice.validator.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    
    private UserRepository userRepository;
    private Validator validator;

    public String registerUser(User user) {
        validator.validateMandatoryFields(user);
        validator.validateUserUniquenessForRegistration(user, userRepository);

    	userRepository.save(user);
        log.info("user with id {} is registered", user.getId());
        return String.format("User registered with id %s, userName %s, firstName %s, lastName %s, and email %s",
                user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public User getUserById(String id) {
        validator.validateUserId(id);
        return userRepository.findById(id).orElseThrow(() ->
                new UserDoesNotExistException("User with ID " + id + " not found"));
    }

    public User getUserByUserName(String userName) {
        validator.validateUserName(userName);
        return userRepository.findByUserName(userName).orElseThrow(() ->
                new UserDoesNotExistException("User with userName " + userName + " not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String updateUser(User user) {
        validator.validateMandatoryFields(user);
        validator.validateUserUniquenessOnUpdate(user, userRepository);

        userRepository.save(user);
        log.info("User details updated for id: {}", user.getId());
        return "User details updated";
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