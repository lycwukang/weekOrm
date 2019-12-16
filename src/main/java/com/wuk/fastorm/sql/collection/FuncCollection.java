package com.wuk.fastorm.sql.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class FuncCollection<T> extends ArrayList<Function<T, ?>> {

    public FuncCollection() {
        this(6);
    }

    public FuncCollection(int size) {
        super(size);
    }

    public static <T> FuncCollection<T> newInstance(Function<T, ?>... functions) {
        FuncCollection<T> funcCollection = new FuncCollection<>();
        Collections.addAll(funcCollection, functions);
        return funcCollection;
    }
}
