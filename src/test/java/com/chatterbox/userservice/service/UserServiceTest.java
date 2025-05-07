package com.chatterbox.userservice.service;

import com.chatterbox.userservice.exception.UserDoesNotExistException;
import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;
import com.chatterbox.userservice.validator.UserServiceValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceValidator validator;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
     void setUp() {
        user = new User();
        user.setId("1");
        user.setUserName("john_doe");
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
    }

    @Test
     void registerUser_validUser_success() {
        // Act
        String result = userService.registerUser(user);

        // Assert
        assertEquals(String.format("User registered with id %s, userName %s, firstName %s, lastName %s, and email %s",
                user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail()), result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
     void updateUser_validUser_success() {
        // Act
        String result = userService.updateUser(user);

        // Assert
        assertEquals("User details updated", result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
     void getUserById_validId_success() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(user.getId());

        // Assert
        assertEquals(user, result);
    }

    @Test
     void getUserById_userNotFound_throwsException() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // Act & Assert
        UserDoesNotExistException exception = assertThrows(UserDoesNotExistException.class, () -> {
            userService.getUserById(user.getId());
        });

        assertEquals("User with ID 1 not found", exception.getMessage());
    }

    @Test
     void getUserByUserName_validUserName_success() {
        // Arrange
        when(userRepository.findByUserName(user.getUserName().toLowerCase())).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByUserName(user.getUserName());

        // Assert
        assertEquals(user, result);
    }

    @Test
     void getUserByUserName_userNotFound_throwsException() {
        // Arrange
        when(userRepository.findByUserName(user.getUserName().toLowerCase())).thenReturn(Optional.empty());

        // Act & Assert
        UserDoesNotExistException exception = assertThrows(UserDoesNotExistException.class, () -> {
            userService.getUserByUserName(user.getUserName());
        });

        assertEquals("User with userName john_doe not found", exception.getMessage());
    }

    @Test
     void deleteUser_userExists_success() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        String result = userService.deleteUser(user.getId());

        // Assert
        assertEquals("User with id 1 is deleted or does not exist", result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
     void deleteUser_userNotFound_success() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // Act
        String result = userService.deleteUser(user.getId());

        // Assert
        assertEquals("User with id 1 is deleted or does not exist", result);
        verify(userRepository, times(0)).delete(user);
    }

    @Test
     void deleteAll_usersExist_success() {
        // Act
        String result = userService.deleteAll();

        // Assert
        assertEquals("All users deleted", result);
        verify(userRepository, times(1)).deleteAll();
    }

    @Test
     void deleteAll_noUsers_success() {
        // Act
        String result = userService.deleteAll();

        // Assert
        assertEquals("All users deleted", result);
        verify(userRepository, times(1)).deleteAll();
    }
}

