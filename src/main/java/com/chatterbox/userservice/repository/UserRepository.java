package com.chatterbox.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatterbox.userservice.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    
	@Query("{ 'userName': ?0 }")
    Optional<User> findByUserName(String userName);
	
	@Query("{ 'id': ?0 }")
    Optional<User> findById(String id);
}