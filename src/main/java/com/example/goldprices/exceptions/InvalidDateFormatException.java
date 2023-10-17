package com.example.goldprices.exceptions;

public class InvalidDateFormatException extends RuntimeException{
    public InvalidDateFormatException() {
        super("Invalid date format. Please use 'yyyy-MM-dd' format.");
    }
    public InvalidDateFormatException(String s) {
        super(s);
    }
}
