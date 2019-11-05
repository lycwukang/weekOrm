package com.wuk.fastorm.exception;

public class FastormBeanAnalyzeException extends FastormException {

    public FastormBeanAnalyzeException(Class<?> clazz) {
        super(clazz.getName());
    }

    public FastormBeanAnalyzeException(Class<?> clazz, String message) {
        super(String.format("[%s]%s", clazz.getName(), message));
    }

    public FastormBeanAnalyzeException(Class<?> clazz, String message, Throwable cause) {
        super(String.format("[%s]%s", clazz.getName(), message), cause);
    }
}
