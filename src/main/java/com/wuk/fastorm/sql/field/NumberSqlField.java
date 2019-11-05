package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * 普通数字
 */
public class NumberSqlField extends AbstractSqlField {

    private Number number;

    public NumberSqlField(Number number) {
        this(number, null);
    }

    public NumberSqlField(Number number, String aliasName) {
        super(aliasName);
        this.number = number;
    }

    @Override
    public String toSql() {
        return number + "";
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
