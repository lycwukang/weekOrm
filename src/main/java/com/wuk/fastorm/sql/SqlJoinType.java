package com.wuk.fastorm.sql;

public enum SqlJoinType {

    EMPTY(""),
    AND("AND"),
    OR("OR");

    private String sql;

    SqlJoinType(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
