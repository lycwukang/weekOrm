package com.wuk.fastorm.sql.impl;

public class SimpleGeneratedKey {

    private Object obj;

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getInt() {
        return Integer.parseInt(obj.toString());
    }

    public long getLong() {
        return Long.parseLong(obj.toString());
    }
}
