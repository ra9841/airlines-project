package com.rabin.airline.project.advice;

import com.rabin.airline.project.exception.InsufficientAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(InsufficientAmountException.class)
    public ResponseEntity<Map<String ,String>> handleBusinessException(InsufficientAmountException ex){
        Map<String ,String > errorMap=new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        errorMap.put("status", HttpStatus.NOT_ACCEPTABLE.toString());
        return ResponseEntity.ok(errorMap);
    }
}
