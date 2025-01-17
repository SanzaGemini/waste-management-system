package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ApiResponse;

public class BindExceptionHandler {
    /**
     * Handles BindException when validation errors occur, 
     * returning a detailed map of errors for each invalid field.
     * @param ex The BindException instance containing validation errors.
     * @return A ResponseEntity containing a map of field errors and associated messages with HTTP status 400 Bad Request.
     */
    public static ResponseEntity<ApiResponse<Object>> handleBindException(List<ObjectError> errors) {
        ApiResponse <Object> response = ApiResponse.error(errors);
        // Return a 400 Bad Request status with the error map
        return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
    }

}
