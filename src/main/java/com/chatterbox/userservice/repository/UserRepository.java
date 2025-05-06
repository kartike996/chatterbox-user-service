package com.chatterbox.userservice.repository;

import com.chatterbox.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * UserRepository is a Spring Data repository interface for the User entity.
 * It extends MongoRepository to provide basic CRUD operations, as well as custom query methods
 * for finding users by their username and email.
 *
 * Key methods:
 * - `findByUserName(String userName)`: Finds a user by their username. The search is case-sensitive,
 *   but the username input is expected to always be in lowercase to ensure consistency.
 * - `findByEmail(String email)`: Finds a user by their email address.
 *
 * This repository acts as an abstraction layer between the service layer and MongoDB,
 * enabling efficient data retrieval and management for user-related operations.
 */
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String userName); // case-sensitive but input is always lowercase
    Optional<User> findByEmail(String email);
}