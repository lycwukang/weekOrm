package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * avg(field)
 */
public class AvgMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public AvgMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public AvgMethodSqlField(SqlField field) {
        this(field, null);
    }

    public AvgMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public AvgMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("AVG(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
