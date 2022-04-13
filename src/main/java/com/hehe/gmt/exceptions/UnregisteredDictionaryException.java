package com.hehe.gmt.exceptions;

public class UnregisteredDictionaryException extends UnknownWordException{
    public UnregisteredDictionaryException() {
    }

    public UnregisteredDictionaryException(String message) {
        super(message);
    }
}
