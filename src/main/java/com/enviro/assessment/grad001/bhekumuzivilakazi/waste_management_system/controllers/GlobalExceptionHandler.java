package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exception.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ResponseClass;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseClass<String>> handleFailedById(NotFoundException eNotFoundExeption){
        return new ResponseEntity<>(NotFoundException
        .failedByMessage(eNotFoundExeption.getMessage())
        ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseClass<String>> handleFailedByMessage(NotFoundException eNotFoundExeption){
        return new ResponseEntity<>(NotFoundException
        .failedByMessage(eNotFoundExeption.getMessage())
        ,HttpStatus.NOT_FOUND);
    }
}
