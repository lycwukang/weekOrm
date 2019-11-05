package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * current_time()
 */
public class CurrentTimeMethodSqlField extends AbstractSqlField {

    public CurrentTimeMethodSqlField(String aliasName) {
        super(aliasName);
    }

    @Override
    public String toSql() {
        return "CURRENT_TIME()";
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
