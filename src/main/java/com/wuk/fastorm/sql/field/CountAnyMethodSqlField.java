package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * count(*)
 */
public class CountAnyMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public CountAnyMethodSqlField() {
        super("");
    }

    public CountAnyMethodSqlField(String aliasName) {
        super(aliasName);
        this.field = new StaticSqlField("*");
    }

    @Override
    public String toSql() {
        return String.format("COUNT(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
