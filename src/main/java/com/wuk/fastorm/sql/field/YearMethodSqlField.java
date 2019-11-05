package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.Collections;
import java.util.List;

/**
 * year(date)
 */
public class YearMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public YearMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public YearMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public YearMethodSqlField(SqlField field) {
        this(field, null);
    }

    public YearMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("YEAR(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
