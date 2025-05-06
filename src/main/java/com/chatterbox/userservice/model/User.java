package com.chatterbox.userservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users_collection")
@Data
public class User {
	
	@Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Username is mandatory")
    private String userName;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    // Lombok will generate setters/getters for other fields,
    // but this setter overrides Lombok’s for `userName`.
    public void setUserName(String userName) {
        this.userName = userName != null ? userName.toLowerCase() : null;
    }
}