package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * upper(field)
 */
public class UpperMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public UpperMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public UpperMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public UpperMethodSqlField(SqlField field) {
        this(field, null);
    }

    public UpperMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("UPPER(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
