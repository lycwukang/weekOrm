package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * least(val0, val1, val2...)
 */
public class LeastMethodSqlField extends AbstractSqlField {

    private SqlField[] numbers;

    public LeastMethodSqlField(SqlField... numbers) {
        this(null, numbers);
    }

    public LeastMethodSqlField(String aliasName, SqlField... numbers) {
        super(aliasName);
        this.numbers = numbers;
    }

    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(numbers[i].getSql());
        }
        return String.format("LEAST(%s)", builder);
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(numbers);
    }
}
