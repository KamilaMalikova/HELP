package com.example.helptest.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7806029002430564887L;

    private String message;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
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
