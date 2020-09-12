package com.example.helptest.exception;

public class ImpossibleConditionException extends RuntimeException {
    private static final long serialVersionUID = -7806024002430564887L;

    private String message;

    public ImpossibleConditionException() {
    }

    public ImpossibleConditionException(String message) {
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
