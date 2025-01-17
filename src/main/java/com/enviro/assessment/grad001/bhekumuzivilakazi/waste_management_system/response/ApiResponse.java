package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response;

import java.util.Map;

/**
 * A generic API response class to standardize the structure of API responses.
 * It supports success, error, and failure scenarios.
 *
 * @param <T> The type of data being returned in the response.
 */
public class ApiResponse<T> {

    private String status;  // Status of the response (e.g., success, error, failed)
    private String message;  // A message related to the status of the response
    private Map<String, String> errorMessages;  // A map for error messages (used for validation errors, etc.)
    private T data;  // The actual data of the response, can be any type

    // Default constructor
    public ApiResponse() {}

    // Constructor for error responses with error messages
    public ApiResponse(String status, Map<String, String> errorMessages) {
        this.status = status;
        this.errorMessages = errorMessages;
        this.data = null;  // No data for error responses
    }

    // Constructor for success responses with data
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorMessages = null;  // No error messages for success
    }

    // Getters and setters for the fields
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Static method for a successful response
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", "Request was successful", data);
    }

    // Static method for an error response with a map of error messages
    public static ApiResponse<Object> error(Map<String, String> errorMessages) {
        return new ApiResponse<>("error", errorMessages);
    }

    // Static method for a failure response with a message
    public static ApiResponse<Object> failure(String message) {
        return new ApiResponse<>("failed", message, null);
    }
}
