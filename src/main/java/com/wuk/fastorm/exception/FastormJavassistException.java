package com.wuk.fastorm.exception;

import javassist.CtClass;

public class FastormJavassistException extends FastormException {

    public FastormJavassistException(CtClass ctClass) {
        super(ctClass.getName());
    }

    public FastormJavassistException(CtClass ctClass, String message) {
        super(String.format("[%s]%s", ctClass.getName(), message));
    }

    public FastormJavassistException(CtClass ctClass, String message, Throwable cause) {
        super(String.format("[%s]%s", ctClass.getName(), message), cause);
    }
}
