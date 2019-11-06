package com.wuk.fastorm.sql.impl;

import java.util.function.Function;

public class FastormGeneratedKey<T> {

    private Function<T, ?> function;

    public FastormGeneratedKey(Function<T, ?> function) {
        this.function = function;
    }

    public Function<T, ?> getFunction() {
        return function;
    }
}
