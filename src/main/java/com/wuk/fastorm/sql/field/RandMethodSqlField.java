package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * rand()
 */
public class RandMethodSqlField extends AbstractSqlField {

    private RandMethodSqlField(String aliasName) {
        super(aliasName);
    }

    @Override
    public String toSql() {
        return "RAND()";
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
