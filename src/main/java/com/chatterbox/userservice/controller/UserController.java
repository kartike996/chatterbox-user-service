package com.chatterbox.userservice.controller;

import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController is a REST controller that handles all HTTP requests related to user operations.
 * It provides endpoints for registering, updating, retrieving, and deleting users, as well as
 * handling invalid endpoint requests.
 *
 * Key Endpoints:
 * - `POST /register`: Registers a new user after validating the provided user data.
 * - `POST /update`: Updates the details of an existing user.
 * - `GET /{id}`: Retrieves a user by their unique ID.
 * - `GET /username/{username}`: Retrieves a user by their username.
 * - `GET /`: Retrieves a list of all users.
 * - `DELETE /delete/{id}`: Deletes a user by their unique ID.
 * - `DELETE /deleteAll`: Deletes all users from the system.
 * - `RequestMapping "/**"`: Fallback handler for invalid or non-existent endpoints, returning a 404 error.

 * The controller delegates business logic to the `UserService` class for processing user data.
 * It uses `@Valid` to ensure incoming data is validated according to the constraints set on the `User` model.
 * Logging is enabled to track user operations and any issues with the requests.
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        log.info("Request received to register user with username: {}", user.getUserName());
        String response = userService.registerUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user) {
        log.info("Request received to update user with id: {}", user.getId());
        String response = userService.updateUser(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        log.info("Fetching user with id: {}", id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
        log.info("Fetching user with username: {}", username);
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        log.info("Request received to delete user with id: {}", id);
        String response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteUserAll() {
        log.warn("Request received to delete all users");
        String response = userService.deleteAll();
        return ResponseEntity.ok(response);
    }

    // Catch-all fallback for invalid sub-paths
    @RequestMapping("**")
    public ResponseEntity<Map<String, Object>> handleInvalidPath() {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Invalid Endpoint");
        body.put("message", "The requested endpoint is not valid. Please check the URL.");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
