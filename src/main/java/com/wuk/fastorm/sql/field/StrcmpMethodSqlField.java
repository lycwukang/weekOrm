package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * strcmp(field, str2)
 */
public class StrcmpMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField str2;

    public StrcmpMethodSqlField(String name, SqlField str2) {
        this(new NameSqlField(name), str2, null);
    }

    public StrcmpMethodSqlField(String name, SqlField str2, String aliasName) {
        this(new NameSqlField(name), str2, aliasName);
    }

    public StrcmpMethodSqlField(SqlField field, SqlField str2) {
        this(field, str2, null);
    }

    public StrcmpMethodSqlField(SqlField field, SqlField str2, String aliasName) {
        super(aliasName);
        this.field = field;
        this.str2 = str2;
    }

    @Override
    public String toSql() {
        return String.format("STRCMP(%s, %s)", field.getSql(), str2.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, str2);
    }
}
