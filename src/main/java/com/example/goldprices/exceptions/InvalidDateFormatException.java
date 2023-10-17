package com.example.goldprices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidDateFormatException extends RuntimeException{
    public InvalidDateFormatException() {
        super("Invalid date format. Please use 'yyyy-MM-dd' format.");
    }
    public InvalidDateFormatException(String s) {
        super(s);
    }
}
