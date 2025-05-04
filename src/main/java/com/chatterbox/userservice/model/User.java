package com.chatterbox.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "users_collection")
@Data
public class User {
	
	@Id
    private String id;
    
    private String userName;
    
    private String email;
}