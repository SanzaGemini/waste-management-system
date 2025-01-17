package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ApiResponse;

public class NotFoundException extends RuntimeException {

    /**
     * Constructor to create an exception with a specific message when an entity is not found by ID.
     *
     * @param type The type of entity (e.g., Category, Tip) that was not found.
     * @param id The ID of the entity that could not be found.
     */
    public NotFoundException(String type, Long id) {
        super(String.format("%s with Id %d not found.", type, id));
    }

    /**
     * Constructor to create a generic exception with a custom message.
     *
     * @param message The custom message describing the exception.
     */
    public NotFoundException(String message) {
        super(message);
    }

    // Optionally, you could add a constructor to handle scenarios where ID might be null.
    public NotFoundException(String type, String message) {
        super(String.format("%s not found: %s", type, message));
    }

    /**
     * Handles NotFoundException globally by returning a 404 response with the exception's message.
     * @param eNotFoundException The NotFoundException instance to be handled.
     * @return A ResponseEntity containing the exception message with HTTP status 404 Not Found.
     */
    

    public static ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException eNotFoundException){
        ApiResponse<Object> response = ApiResponse.failure(eNotFoundException.getMessage());
        // Return a 404 status with the exception message
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
