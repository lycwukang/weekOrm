package com.wuk.fastorm.exception;

public class FastormSqlException extends FastormException {

    public FastormSqlException() {
    }

    public FastormSqlException(String message) {
        super(message);
    }

    public FastormSqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
