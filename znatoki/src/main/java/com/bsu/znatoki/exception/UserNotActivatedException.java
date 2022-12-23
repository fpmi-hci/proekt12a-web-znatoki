package com.bsu.znatoki.exception;

public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String msg) {
        super(msg);
    }
}