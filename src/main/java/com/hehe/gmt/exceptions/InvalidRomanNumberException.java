package com.hehe.gmt.exceptions;

public class InvalidRomanNumberException extends UnknownWordException{
    public InvalidRomanNumberException() {
    }

    public InvalidRomanNumberException(String message) {
        super(message);
    }
}
