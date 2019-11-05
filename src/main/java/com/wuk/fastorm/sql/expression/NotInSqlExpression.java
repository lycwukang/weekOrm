package com.wuk.fastorm.sql.expression;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * x not in (y, z)
 */
public class NotInSqlExpression extends AbstractSqlExpression {

    private SqlField x;
    private SqlField[] vars;

    public NotInSqlExpression(SqlField x, SqlField[] vars) {
        this.x = x;
        this.vars = vars;
    }

    @Override
    public String getSql() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < vars.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(vars[i].getSql());
        }
        return String.format("%s NOT IN (%s)", x.getSql(), builder);
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParamsAndSingle(x, vars);
    }
}
