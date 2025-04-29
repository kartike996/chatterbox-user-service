package com.chatterbox.userservice.controller;

import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("getAll")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}