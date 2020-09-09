package com.example.helptest.exception;

public class ConstraintException extends RuntimeException{

    private String message;

    public ConstraintException() {
    }

    public ConstraintException(String message) {
//        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
