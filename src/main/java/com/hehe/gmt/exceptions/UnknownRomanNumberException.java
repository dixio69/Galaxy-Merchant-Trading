package com.hehe.gmt.exceptions;

public class UnknownRomanNumberException extends UnknownWordException{
    public UnknownRomanNumberException() {
    }

    public UnknownRomanNumberException(String message) {
        super(message);
    }
}
