package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * now()
 */
public class NowMethodSqlField extends AbstractSqlField {

    public NowMethodSqlField(String aliasName) {
        super(aliasName);
    }

    @Override
    public String toSql() {
        return "NOW()";
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
