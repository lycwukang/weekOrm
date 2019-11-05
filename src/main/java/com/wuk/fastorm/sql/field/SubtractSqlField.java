package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * field - x
 */
public class SubtractSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField subtraction;

    public SubtractSqlField(String name, SqlField subtraction) {
        this(new NameSqlField(name), subtraction, null);
    }

    public SubtractSqlField(String name, SqlField subtraction, String aliasName) {
        this(new NameSqlField(name), subtraction, aliasName);
    }

    public SubtractSqlField(SqlField field, SqlField subtraction) {
        this(field, subtraction, null);
    }

    public SubtractSqlField(SqlField field, SqlField subtraction, String aliasName) {
        super(aliasName);
        this.field = field;
        this.subtraction = subtraction;
    }

    @Override
    public String toSql() {
        return String.format("%s - %s", field.getSql(), subtraction.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, subtraction);
    }
}
