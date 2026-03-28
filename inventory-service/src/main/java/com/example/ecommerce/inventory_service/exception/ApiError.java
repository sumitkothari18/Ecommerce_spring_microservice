package com.example.ecommerce.inventory_service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private String error;
    private HttpStatus statusCode;
    private LocalDateTime timeStamp;

    public ApiError()
    {
        this.timeStamp=LocalDateTime.now();
    }

    public ApiError(String error,HttpStatus statusCode)
    {
        this.error=error;
        this.statusCode=statusCode;
    }
}
