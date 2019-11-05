package com.wuk.fastorm.sql.impl;

import java.util.function.Function;

public class FastormGeneratedKey<T> {

    private Function<T, Object> function;

    public FastormGeneratedKey(Function<T, Object> function) {
        this.function = function;
    }

    public Function<T, Object> getFunction() {
        return function;
    }
}
