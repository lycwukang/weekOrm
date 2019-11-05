package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * bit_length(field)
 */
public class BitLengthMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public BitLengthMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public BitLengthMethodSqlField(SqlField field) {
        this(field, null);
    }

    public BitLengthMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("BIT_LENGTH(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
