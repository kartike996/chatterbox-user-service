package com.chatterbox.userservice.service;

import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User register(User user) throws Exception {
    	
    	if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new Exception("User already exists");
        }
    	userRepository.save(user);
        return user;
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
}