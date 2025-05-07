package com.chatterbox.userservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User represents a user entity within the system. This class is annotated with
 * @Document to specify that it corresponds to a MongoDB collection.
 *
 * The fields of the User class are mapped to the respective attributes of a user,
 * such as username, first name, last name, and email. These fields are also validated
 * using Jakarta Validation annotations such as @NotBlank and @Email to ensure data integrity
 * and correctness when interacting with the system.
 *
 * Key features of the User class:
 * - The `userName` field is indexed and unique, ensuring no two users can have the same username.
 * - The `userName` setter ensures that the username is stored in lowercase, making it case-insensitive.
 * - The `@Indexed` annotation on the `userName` field enforces uniqueness at the database level.
 * - The class uses Lombok annotations (@Data) to automatically generate getters, setters, and other boilerplate code.
 *
 * The User class plays a critical role in the system by representing the core user information
 * that drives various operations such as registration, authentication, and user management.
 */
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