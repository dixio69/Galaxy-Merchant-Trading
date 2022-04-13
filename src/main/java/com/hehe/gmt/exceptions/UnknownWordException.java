package com.hehe.gmt.exceptions;

public class UnknownWordException extends Exception{
    public UnknownWordException() {
    }

    public UnknownWordException(String message) {
        super(message);
    }
}
