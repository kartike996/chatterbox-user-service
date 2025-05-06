package com.chatterbox.userservice.validator;

import com.chatterbox.userservice.exception.MandatoryFieldException;
import com.chatterbox.userservice.exception.UserAlreadyExistsException;
import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceValidatorTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;

    private UserServiceValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserServiceValidator();
    }

    @Test
    void validateMandatoryFieldsWithBlankFirstNameThrowsException() {
        // Arrange
        when(user.getFirstName()).thenReturn(null);
        
        // Act
        MandatoryFieldException exception = assertThrows(MandatoryFieldException.class,
                () -> validator.validateMandatoryFields(user));

        // Assert
        assertEquals("The field firstName is mandatory and cannot be null or empty.", exception.getMessage());
    }

    @Test
    void validateMandatoryFieldsWithBlankUserNameThrowsException() {
        // Arrange
        when(user.getFirstName()).thenReturn("firstName");
        when(user.getUserName()).thenReturn(null);

        // Act
        MandatoryFieldException exception = assertThrows(MandatoryFieldException.class,
                () -> validator.validateMandatoryFields(user));

        // Assert
        assertEquals("The field userName is mandatory and cannot be null or empty.", exception.getMessage());
    }

    @Test
    void validateMandatoryFieldsWithBlankEmailThrowsException() {
        // Arrange
        when(user.getFirstName()).thenReturn("firstName");
        when(user.getUserName()).thenReturn("username");
        when(user.getEmail()).thenReturn(null);

        // Act
        MandatoryFieldException exception = assertThrows(MandatoryFieldException.class,
                () -> validator.validateMandatoryFields(user));

        // Assert
        assertEquals("The field email is mandatory and cannot be null or empty.", exception.getMessage());
    }

    @Test
    void validateMandatoryFieldsWithValidInputsDoNotThrowException() {
        // Arrange
        when(user.getFirstName()).thenReturn("firstName");
        when(user.getUserName()).thenReturn("username");
        when(user.getEmail()).thenReturn("email@email.com");

        // Act and Assert
        assertDoesNotThrow(
                () -> validator.validateMandatoryFields(user));
    }

    @Test
    void validateUserIdWithBlankIdThrowsException() {
        // Act
        MandatoryFieldException exception = assertThrows(MandatoryFieldException.class,
                () -> validator.validateUserId(null));

        // Assert
        assertEquals("The field id is mandatory and cannot be null or empty.", exception.getMessage());
    }

    @Test
    void validateUserIdWithValidIdDoesNotThrowException() {
        // Act and Assert
        assertDoesNotThrow(
                () -> validator.validateUserId("id"));
    }

    @Test
     void validateUserUniquenessForRegistration_validUser_noDuplicate() {
        // Arrange: mock repository to return empty results for username and email
        when(userRepository.findByUserName(user.getUserName())).thenReturn(java.util.Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.empty());

        // Act: validate user
        assertDoesNotThrow(() -> validator.validateUserUniquenessForRegistration(user, userRepository));

        // Verify that the repository methods were called
        verify(userRepository, times(1)).findByUserName(user.getUserName());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
     void validateUserUniquenessForRegistration_duplicateUserName_throwsException() {
        // Arrange:
        when(user.getUserName()).thenReturn("user");
        when(user.getId()).thenReturn("id");

        User existingUser = new User();
        existingUser.setId("2");
        existingUser.setUserName("different_user");
        existingUser.setEmail("john.doe@example.com");

        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(existingUser));

        // Act & Assert: exception is thrown due to duplicate username
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            validator.validateUserUniquenessForRegistration(user, userRepository);
        });

        // Assert: verify the exception message
        assertEquals("Username " + user.getUserName() + " already exists.", exception.getMessage());

        // Verify that the repository methods were called
        verify(userRepository, times(1)).findByUserName(user.getUserName());
    }

    @Test
     void validateUserUniquenessForRegistration_duplicateEmail_throwsException() {
        // Arrange
        when(user.getEmail()).thenReturn("email@email.com");
        when(user.getId()).thenReturn("id");

        User existingUser = new User();
        existingUser.setId("2");
        existingUser.setUserName("different_user");
        existingUser.setEmail("john.doe@example.com");

        when(userRepository.findByUserName(user.getUserName())).thenReturn(java.util.Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.of(existingUser));

        // Act & Assert: exception is thrown due to duplicate email
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            validator.validateUserUniquenessForRegistration(user, userRepository);
        });

        // Assert: verify the exception message
        assertEquals("Email " + user.getEmail() + " already exists.", exception.getMessage());

        // Verify that the repository methods were called
        verify(userRepository, times(1)).findByUserName(user.getUserName());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
     void validateUserUniquenessOnUpdate_duplicateUserName_throwsException() {
        // Arrange:
        when(user.getUserName()).thenReturn("user");
        when(user.getId()).thenReturn("id");

        User existingUser = new User();
        existingUser.setId("2");
        existingUser.setUserName("different_user");
        existingUser.setEmail("john.doe@example.com");

        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(existingUser));

        // Act & Assert: exception is thrown due to duplicate username
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            validator.validateUserUniquenessOnUpdate(user, userRepository);
        });

        // Assert: verify the exception message
        assertEquals("Username " + user.getUserName() + " already exists.", exception.getMessage());

        // Verify that the repository methods were called
        verify(userRepository, times(1)).findByUserName(user.getUserName());
    }

    @Test
     void validateUserUniquenessOnUpdate_duplicateEmail_throwsException() {
        // Arrange
        when(user.getEmail()).thenReturn("email@email.com");
        when(user.getId()).thenReturn("id");

        User existingUser = new User();
        existingUser.setId("2");
        existingUser.setUserName("different_user");
        existingUser.setEmail("john.doe@example.com");

        when(userRepository.findByUserName(user.getUserName())).thenReturn(java.util.Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.of(existingUser));

        // Act & Assert: exception is thrown due to duplicate email
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            validator.validateUserUniquenessOnUpdate(user, userRepository);
        });

        // Assert: verify the exception message
        assertEquals("Email " + user.getEmail() + " already exists.", exception.getMessage());

        // Verify that the repository methods were called
        verify(userRepository, times(1)).findByUserName(user.getUserName());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }
}

