package com.rabin.airline.project.exception;

public class InsufficientAmountException extends RuntimeException{

    public InsufficientAmountException(String message){
        super(message);
    }
}
