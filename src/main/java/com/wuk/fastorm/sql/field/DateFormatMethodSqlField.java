package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * date_format(field, format)
 */
public class DateFormatMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private String format;

    public DateFormatMethodSqlField(String name, String format) {
        this(new NameSqlField(name), format, null);
    }

    public DateFormatMethodSqlField(String name, String format, String aliasName) {
        this(new NameSqlField(name), format, aliasName);
    }

    public DateFormatMethodSqlField(SqlField field, String format) {
        this(field, format, null);
    }

    public DateFormatMethodSqlField(SqlField field, String format, String aliasName) {
        super(aliasName);
        this.field = field;
        this.format = format;
    }

    @Override
    public String toSql() {
        return String.format("DATE_FORMAT(%s, '%s')", field.getSql(), format);
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
