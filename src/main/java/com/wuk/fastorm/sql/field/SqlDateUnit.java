package com.wuk.fastorm.sql.field;

public enum SqlDateUnit {

    MICROSECOND("MICROSECOND"),
    SECOND("SECOND"),
    MINUTE("MINUTE"),
    HOUR("HOUR"),
    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH"),
    QUARTER("QUARTER"),
    YEAR("YEAR"),
    SECOND_MICROSECOND("SECOND_MICROSECOND"),
    MINUTE_MICROSECOND("MINUTE_MICROSECOND"),
    MINUTE_SECOND("MINUTE_SECOND"),
    HOUR_MICROSECOND("HOUR_MICROSECOND"),
    HOUR_SECOND("HOUR_SECOND"),
    HOUR_MINUTE("HOUR_MINUTE"),
    DAY_MICROSECOND("DAY_MICROSECOND"),
    DAY_SECOND("DAY_SECOND"),
    DAY_MINUTE("DAY_MINUTE"),
    DAY_HOUR("DAY_HOUR"),
    YEAR_MONTH("YEAR_MONTH");

    private String sql;

    SqlDateUnit(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
