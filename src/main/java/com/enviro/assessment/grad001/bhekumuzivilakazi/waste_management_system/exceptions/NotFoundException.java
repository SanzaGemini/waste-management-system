package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ResponseClass;

public final class NotFoundException extends RuntimeException{
    
    public  NotFoundException(String type,Long id){
        super(type +" with Id "+id+" not found.");
    }

    public static <T> ResponseClass<T> failedById(String type,Long id){
            String errorMessage = new NotFoundException(type,id).getMessage();
        return new ResponseClass<>("failed",null,errorMessage);
    }

    public static <T> ResponseClass<T> failedByMessage(String message){
       
    return new ResponseClass<>("failed",null,message);
}

}
