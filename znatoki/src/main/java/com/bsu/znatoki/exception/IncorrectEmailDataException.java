package com.bsu.znatoki.exception;

public class IncorrectEmailDataException extends RuntimeException {
    public IncorrectEmailDataException(Exception exc) {
        super(exc);
    }
}
