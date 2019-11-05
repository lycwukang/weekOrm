package com.wuk.fastorm.sql;

public enum SqlOrderType {

    ASC("ASC"),
    DESC("DESC");

    private String sql;

    SqlOrderType(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
