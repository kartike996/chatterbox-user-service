package com.chatterbox.userservice.controller;

import com.chatterbox.userservice.model.User;
import com.chatterbox.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Mock MVC to send requests and verify responses

    @MockBean
    private UserService userService;  // Mocking the service layer

    @Autowired
    private ObjectMapper objectMapper;  // Used to convert objects to JSON format for requests

    private User sampleUser;

    @BeforeEach
    public void setUp() {
        sampleUser = new User();
        sampleUser.setId("1");
        sampleUser.setUserName("john_doe");
        sampleUser.setFirstName("John");
        sampleUser.setLastName("Doe");
        sampleUser.setEmail("john.doe@example.com");
    }

    @Test
    public void registerUser() throws Exception {
        // Arrange
        String userJson = objectMapper.writeValueAsString(sampleUser);
        String successMessage = String.format("User registered with id %s, userName %s, firstName %s, lastName %s, and email %s",
        sampleUser.getId(), sampleUser.getUserName(), sampleUser.getFirstName(), sampleUser.getLastName(), sampleUser.getEmail());
        when(userService.registerUser(any(User.class))).thenReturn(successMessage);

        // Act
        ResultActions result = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().string(successMessage));
        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void updateUser() throws Exception {
        // Arrange
        String userJson = objectMapper.writeValueAsString(sampleUser);
        String successMessage = "User details updated";
        when(userService.updateUser(any(User.class))).thenReturn(successMessage);

        // Act
        ResultActions result = mockMvc.perform(post("/api/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().string(successMessage));
        verify(userService, times(1)).updateUser(any(User.class));
    }

    @Test
    public void getUserById() throws Exception {
        // Arrange
        when(userService.getUserById("1")).thenReturn(sampleUser);

        // Act
        ResultActions result = mockMvc.perform(get("/api/users/1"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("john_doe"))
                .andExpect(jsonPath("$.firstName").value("John"));
        verify(userService, times(1)).getUserById("1");
    }

    @Test
    public void getUserByUserName() throws Exception {
        // Arrange
        when(userService.getUserByUserName("john_doe")).thenReturn(sampleUser);

        // Act
        ResultActions result = mockMvc.perform(get("/api/users/username/john_doe"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("john_doe"))
                .andExpect(jsonPath("$.firstName").value("John"));
        verify(userService, times(1)).getUserByUserName("john_doe");
    }

    @Test
    public void getAllUsers() throws Exception {
        // Arrange
        when(userService.getAll()).thenReturn(List.of(sampleUser));

        // Act
        ResultActions result = mockMvc.perform(get("/api/users"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("john_doe"))
                .andExpect(jsonPath("$[0].firstName").value("John"));
        verify(userService, times(1)).getAll();
    }

    @Test
    public void deleteUser() throws Exception {
        // Arrange
        String successMessage = "User with id " + sampleUser.getId() + " is deleted or does not exist";
        when(userService.deleteUser("1")).thenReturn(successMessage);

        // Act
        ResultActions result = mockMvc.perform(delete("/api/users/delete/1"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().string(successMessage));
        verify(userService, times(1)).deleteUser("1");
    }

    @Test
    public void deleteAllUsers() throws Exception {
        // Arrange
        String successMessage = "All users deleted";
        when(userService.deleteAll()).thenReturn(successMessage);

        // Act
        ResultActions result = mockMvc.perform(delete("/api/users/deleteAll"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().string(successMessage));
        verify(userService, times(1)).deleteAll();
    }

    @Test
    public void handleInvalidPath() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/api/users/deleteMe/id"));

        // Assert
        result.andExpect(status().isNotFound());
    }
}
