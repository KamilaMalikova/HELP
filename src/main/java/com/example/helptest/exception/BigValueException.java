package com.example.helptest.exception;

public class BigValueException extends RuntimeException {
    private static final long serialVersionUID = -7806029002630564887L;

    private String message;

    public BigValueException() {
    }

    public BigValueException(String message) {
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
