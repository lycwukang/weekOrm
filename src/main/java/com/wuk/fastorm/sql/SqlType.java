package com.wuk.fastorm.sql;

public enum SqlType {

    SELECT("SELECT"),
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private String sql;

    SqlType(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
