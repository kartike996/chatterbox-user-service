package com.chatterbox.userservice.service;

import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    
    @Autowired private UserRepository userRepository;

    public User registerUser(User user) throws Exception {
    	if (userRepository.findByUserName(user.getUserName()).isPresent()
        && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("User already exists");
        }
    	userRepository.save(user);
        log.info("user with id {} is registered", user.getId());
        return user;
    }

    public String updateUser(User user) throws Exception {
        if(Strings.isBlank(user.getUserName())) {
            throw new Exception("userName could not be blank");
        }
        if(Strings.isBlank(user.getEmail())) {
            throw new Exception("email could not be blank");
        }
        userRepository.save(user);
        log.info("User details updated");
        return "User details updated";
    }

    public User getUserById(String id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public User getUserByUserName(String userName) throws Exception {
        return userRepository.findByUserName(userName).orElseThrow(() -> new Exception("User not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String deleteUser(String id) {
        userRepository.findById(id).ifPresent(user -> userRepository.delete(user));
        log.info("User with id {} is deleted or does not exist", id);
        return "User with id " + id + " is deleted or does not exist";
    }
}