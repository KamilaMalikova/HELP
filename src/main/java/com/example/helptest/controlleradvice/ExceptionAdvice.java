package com.example.helptest.controlleradvice;

import com.example.helptest.exception.*;
import com.example.helptest.exception.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDetails handleDuplicateException(DuplicateException se) {
        ErrorDetails response = new ErrorDetails(new Date(), se.getMessage());
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleIllegalArgumentException(IllegalArgumentException se) {
        ErrorDetails response = new ErrorDetails(new Date(), se.getMessage());
        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails handleNotFoundException(NotFoundException se) {
        ErrorDetails response = new ErrorDetails(new Date(), se.getMessage());
        return response;
    }

    @ExceptionHandler(BigValueException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorDetails handleBigValueException(BigValueException se) {
        ErrorDetails response = new ErrorDetails(new Date(), se.getMessage());
        return response;
    }

    @ExceptionHandler(ImpossibleConditionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetails handleImpossibleConditionException(ImpossibleConditionException se) {
        ErrorDetails response = new ErrorDetails(new Date(), se.getMessage());
        return response;
    }
}
