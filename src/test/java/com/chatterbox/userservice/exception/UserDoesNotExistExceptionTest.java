package com.chatterbox.userservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserDoesNotExistExceptionTest {

    @Test
    void instance() {
        String errorMessage = "message";
        UserDoesNotExistException exception = new UserDoesNotExistException(errorMessage);
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}
