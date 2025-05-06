# ChatterBox User Service

The **ChatterBox User Service** is a microservice responsible for managing user information in the **ChatterBox** platform. It provides endpoints for user registration, updating, retrieval, and deletion, ensuring that user data is validated and consistent.

## Project Overview

- **Name**: ChatterBox User Service
- **Version**: 0.0.1-SNAPSHOT
- **Description**: User management microservice for ChatterBox platform
- **Java Version**: 21
- **Spring Boot Version**: 3.2.4
- **MongoDB**: Used as the database for storing user data.
- **Server Port**: 9091

## Features

- User Registration
- User Update
- Retrieve User by ID or Username
- Delete User or All Users
- Validation of mandatory fields, uniqueness of username and email
- Logging and error handling

## Endpoints

The following endpoints are available for interacting with the User Service:

### 1. Register a new user
- **URL**: `/api/users/register`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "userName": "johndoe",
        "firstName": "John",
        "lastName": "Doe",
        "email": "johndoe@example.com"
    }
    ```
- **Response**:
    ```json
    {
        "message": "User registered with id <id>, userName <username>, firstName <firstname>, lastName <lastname>, and email <email>"
    }
    ```

### 2. Update user information
- **URL**: `/api/users/update`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "id": "<user-id>",
        "userName": "johnnydoe",
        "firstName": "Johnny",
        "lastName": "Doe",
        "email": "johnnydoe@example.com"
    }
    ```
- **Response**:
    ```json
    {
        "message": "User details updated"
    }
    ```

### 3. Get user by ID
- **URL**: `/api/users/{id}`
- **Method**: `GET`
- **Response**:
    ```json
    {
        "id": "<id>",
        "userName": "johnnydoe",
        "firstName": "Johnny",
        "lastName": "Doe",
        "email": "johnnydoe@example.com"
    }
    ```

### 4. Get user by Username
- **URL**: `/api/users/username/{username}`
- **Method**: `GET`
- **Response**:
    ```json
    {
        "id": "<id>",
        "userName": "johnnydoe",
        "firstName": "Johnny",
        "lastName": "Doe",
        "email": "johnnydoe@example.com"
    }
    ```

### 5. Get all users
- **URL**: `/api/users`
- **Method**: `GET`
- **Response**:
    ```json
    [
        {
            "id": "<id>",
            "userName": "johnnydoe",
            "firstName": "Johnny",
            "lastName": "Doe",
            "email": "johnnydoe@example.com"
        },
        ...
    ]
    ```

### 6. Delete user by ID
- **URL**: `/api/users/delete/{id}`
- **Method**: `DELETE`
- **Response**:
    ```json
    {
        "message": "User with id <id> is deleted or does not exist"
    }
    ```

### 7. Delete all users
- **URL**: `/api/users/deleteAll`
- **Method**: `DELETE`
- **Response**:
    ```json
    {
        "message": "All users deleted"
    }
    ```

### 8. Invalid Endpoint
- **URL**: `/**`
- **Method**: `ALL`
- **Response**:
    ```json
    {
        "timestamp": "2025-05-06T00:00:00",
        "status": 404,
        "error": "Invalid Endpoint",
        "message": "The requested endpoint is not valid. Please check the URL."
    }
    ```

## Technology Stack

- **Java 21**: Primary programming language.
- **Spring Boot 3.2.4**: Framework for building the microservice.
- **MongoDB**: NoSQL database used to store user data.
- **Lombok**: Used for reducing boilerplate code.
- **Jakarta Validation API**: Used for validating the user input.

## Running the Service

### Prerequisites

- JDK 21 installed
- MongoDB running locally or configured to connect to a remote instance

### Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd chatterbox-user-service
