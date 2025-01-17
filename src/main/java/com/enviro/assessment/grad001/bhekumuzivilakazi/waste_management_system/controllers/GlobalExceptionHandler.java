package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.NotFoundException;

/**
 * Global exception handler for handling application-wide exceptions.
 * This class is annotated with @ControllerAdvice and provides centralized 
 * exception handling for the controllers in the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles NotFoundException globally by returning a 404 response with the exception's message.
     * @param eNotFoundException The NotFoundException instance to be handled.
     * @return A ResponseEntity containing the exception message with HTTP status 404 Not Found.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException eNotFoundException){
        // Return a 404 status with the exception message
        return new ResponseEntity<>(eNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles BindException globally when validation errors occur, 
     * returning a detailed map of errors for each invalid field.
     * @param ex The BindException instance containing validation errors.
     * @return A ResponseEntity containing a map of field errors and associated messages with HTTP status 400 Bad Request.
     */
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        // Extract field validation errors
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        
        // Create a map to store field names and their corresponding error messages
        Map<String, String> errorMap = new HashMap<>();
        
        // Populate the error map with the field errors
        for (FieldError fieldError : fieldErrors) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Return a 400 Bad Request status with the error map
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
