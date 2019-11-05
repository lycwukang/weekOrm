package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * count(*)
 */
public class CountMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public CountMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public CountMethodSqlField(SqlField field) {
        this(field, null);
    }

    public CountMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public CountMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
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
