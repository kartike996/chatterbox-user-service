package com.chatterbox.userservice.repository;

import com.chatterbox.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
}