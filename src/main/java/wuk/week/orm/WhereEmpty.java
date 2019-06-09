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
        List<Pair<Field, Object>> list = new ArrayList<>();
        if (where != null) {
            list.addAll(where.builderObj());
        }
        return list;
    }

    @Override
    public Where and(Where where) {
        return this.where = where;
    }

    @Override
    public Where or(Where where) {
        return this.where = where;
    }
}