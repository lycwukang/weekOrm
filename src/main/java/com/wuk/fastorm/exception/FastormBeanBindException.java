package com.wuk.fastorm.exception;

public class FastormBeanBindException extends FastormException {

    public FastormBeanBindException(Class<?> clazz) {
        super(clazz.getName());
    }

    public FastormBeanBindException(Class<?> clazz, String message) {
        super(String.format("[%s]%s", clazz.getName(), message));
    }

    public FastormBeanBindException(Class<?> clazz, String message, Throwable cause) {
        super(String.format("[%s]%s", clazz.getName(), message), cause);
    }
}
