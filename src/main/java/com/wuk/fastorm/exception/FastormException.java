package com.wuk.fastorm.exception;

public class FastormException extends RuntimeException {

    public FastormException() {
    }

    public FastormException(String message) {
        super(message);
    }

    public FastormException(String message, Throwable cause) {
        super(message, cause);
    }
}
