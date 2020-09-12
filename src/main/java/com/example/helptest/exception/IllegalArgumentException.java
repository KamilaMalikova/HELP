package com.example.helptest.exception;

public class IllegalArgumentException extends RuntimeException {
    private static final long serialVersionUID = -7806024002430564887L;

    private String message;

    public IllegalArgumentException() {
    }

    public IllegalArgumentException(String message) {
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
