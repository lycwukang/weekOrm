package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * pi()
 */
public class PiMethodSqlField extends AbstractSqlField {

    public PiMethodSqlField(String aliasName) {
        super(aliasName);
    }

    @Override
    public String toSql() {
        return "PI()";
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
