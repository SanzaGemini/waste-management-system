package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response;

public class ResponseClass<T> {
    private String status;
    private T data;
    private String message;

    public ResponseClass() {
    }

    public ResponseClass(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ResponseClass(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Static method to create a success response
    public static <T> ResponseClass<T> successResponse(String message, T data) {
        return new ResponseClass<>("success", data, message);
    }

    // Overloaded method for success without data
    public static <T> ResponseClass<T> failedResponse(String message) {
        return new ResponseClass<>("fail", null, message);
    }

}
