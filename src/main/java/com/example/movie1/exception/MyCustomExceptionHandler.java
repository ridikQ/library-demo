package com.example.movie1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Component
@RestControllerAdvice
public class MyCustomExceptionHandler {

@ExceptionHandler(value = {CustomBookException.class})
protected ResponseEntity<Object> handleUserExceptions(RuntimeException ex) {
    ErrorFormat errorBody = new ErrorFormat();
    errorBody.setMessage(ex.getMessage());
    errorBody.setTimeStamp(new Date());
    errorBody.setPath("users/add");
    errorBody.setSuggestion("Do not leave the field empty");

    return new ResponseEntity<Object>(errorBody, HttpStatus.BAD_REQUEST);
}
}
