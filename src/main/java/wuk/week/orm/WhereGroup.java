package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.List;

public class WhereGroup implements Where {

    private Where where;

    public WhereGroup(Where where) {
        this.where = where;
    }

    @Override
    public void builder(StringBuilder builder) {
        this.where.builder(builder);
    }

    @Override
    public List<Pair<Field, Object>> builderObj() {
        return this.where.builderObj();
    }

    @Override
    public Where and(Where where) {
        return this.where.and(where);
    }

    @Override
    public Where or(Where where) {
        return this.where.or(where);
    }
}