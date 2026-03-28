package com.example.ecommerce.inventory_service.exception;

public class BadExceptionError extends RuntimeException{

    public BadExceptionError(String message){
        super(message);
    }
}
