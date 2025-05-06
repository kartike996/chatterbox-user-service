package com.chatterbox.userservice.repository;

import com.chatterbox.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String userName); // case-sensitive but input is always lowercase
    Optional<User> findByEmail(String email);
}