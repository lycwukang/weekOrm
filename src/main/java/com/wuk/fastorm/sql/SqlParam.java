package com.wuk.fastorm.sql;

public class SqlParam {

    private Class<?> clazz;
    private Object obj;

    public SqlParam(Class<?> clazz, Object obj) {
        this.clazz = clazz;
        this.obj = obj;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getObj() {
        return obj;
    }
}
