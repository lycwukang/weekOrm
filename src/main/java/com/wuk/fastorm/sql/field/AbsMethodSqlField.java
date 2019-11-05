package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * abs(field)
 */
public class AbsMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public AbsMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public AbsMethodSqlField(SqlField field) {
        this(field, null);
    }

    public AbsMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public AbsMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("ABS(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
