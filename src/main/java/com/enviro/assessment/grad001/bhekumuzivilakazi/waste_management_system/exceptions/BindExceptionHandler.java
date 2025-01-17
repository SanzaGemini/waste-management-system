package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ApiResponse;

public class BindExceptionHandler {
    /**
     * Handles BindException when validation errors occur, 
     * returning a detailed map of errors for each invalid field.
     * @param ex The BindException instance containing validation errors.
     * @return A ResponseEntity containing a map of field errors and associated messages with HTTP status 400 Bad Request.
     */
    private static ResponseEntity<ApiResponse<Object>> handleBindException(Map<String,String> errors) {
        ApiResponse <Object> response = ApiResponse.error(errors);
        // Return a 400 Bad Request status with the error map
        return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ApiResponse<Object>> handleBindException(MethodArgumentNotValidException ex) {
        // Extract field validation errors
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        
        // Create a map to store field names and their corresponding error messages
        Map<String, String> errorMap = new HashMap<>();
        
        // Populate the error map with the field errors
        for (FieldError fieldError : fieldErrors) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Return a 400 Bad Request status with the error map
        return handleBindException(errorMap);
    }

}
