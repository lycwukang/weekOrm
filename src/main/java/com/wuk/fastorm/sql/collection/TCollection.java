package com.wuk.fastorm.sql.collection;

import java.util.ArrayList;
import java.util.Collections;

public class TCollection<T> extends ArrayList<T> {

    public TCollection() {
        this(6);
    }

    public TCollection(int size) {
        super(size);
    }

    @SafeVarargs
    public static <T> TCollection<T> newInstance(T... ts) {
        TCollection<T> tCollection = new TCollection<>(ts.length);
        Collections.addAll(tCollection, ts);
        return tCollection;
    }
}
