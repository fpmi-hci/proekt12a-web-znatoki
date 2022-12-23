package com.bsu.znatoki.exception;


public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException(Class<?> clazz, int id) {
        super(String.format("Entity of class %s with id %d not found", clazz.getName(), id));
    }

    public NotFoundEntityException(String msg) {
        super(msg);
    }
}
