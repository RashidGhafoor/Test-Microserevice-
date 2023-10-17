package com.example.goldprices.controller;

import com.example.goldprices.exceptions.DateValueNotFoundException;
import com.example.goldprices.exceptions.InvalidDateFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = InvalidDateFormatException.class)
    public ResponseEntity<String> invalidDateFormatExceptionHandler(InvalidDateFormatException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = DateValueNotFoundException.class)
    public ResponseEntity<String> dateNotFoundExceptionHandler(DateValueNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
