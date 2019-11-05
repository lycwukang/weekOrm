package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlOrder;
import com.wuk.fastorm.sql.SqlOrderType;
import com.wuk.fastorm.sql.SqlParam;

import java.util.List;

public class StandardSqlSqlOrder implements SqlOrder {

    private SqlOrderType orderType;
    private SqlField field;

    public StandardSqlSqlOrder(SqlField field, SqlOrderType orderType) {
        this.field = field;
        this.orderType = orderType;
    }

    @Override
    public String getSql() {
        return String.format("%s %s", field.getSql(), orderType.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return field.getParams();
    }
}
