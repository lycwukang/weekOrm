package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * lower(field)
 */
public class LowerMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public LowerMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public LowerMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public LowerMethodSqlField(SqlField field) {
        this(field, null);
    }

    public LowerMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("LOWER(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
