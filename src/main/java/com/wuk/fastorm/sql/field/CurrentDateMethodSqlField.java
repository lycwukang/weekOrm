package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * current_date()
 */
public class CurrentDateMethodSqlField extends AbstractSqlField {

    public CurrentDateMethodSqlField(String aliasName) {
        super(aliasName);
    }

    @Override
    public String toSql() {
        return "CURRENT_DATE()";
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
