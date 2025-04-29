package com.chatterbox.userservice.service;

import com.chatterbox.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> mockDb = new ArrayList<>();

    public User register(User user) {
        user.setId("mock-" + (mockDb.size() + 1));
        mockDb.add(user);
        return user;
    }

    public User getUserById(String id) {
        return mockDb.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public User getUserByUserName(String userName) {
        return mockDb.stream().filter(user -> user.getUserName().equals(userName)).findFirst().orElse(null);
    }

    public List<User> getAll() {
        return mockDb;
    }
}