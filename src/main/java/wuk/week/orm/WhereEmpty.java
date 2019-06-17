package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class WhereEmpty implements Where {

    private Where where;

    @Override
    public void builder(StringBuilder builder) {
        if (where != null) {
            builder.append(" where ");
            where.builder(builder);
        }
    }

    @Override
    public List<Pair<Field, Object>> builderObj() {
        if (where != null) {
            return where.builderObj();
        }
        return new ArrayList<>();
    }

    @Override
    public Where and(Where where) {
        if (this.where == null) {
            return this.where = where;
        } else {
            return this.where.and(where);
        }
    }

    @Override
    public Where or(Where where) {
        if (this.where == null) {
            return this.where = where;
        } else {
            return this.where.or(where);
        }
    }
}