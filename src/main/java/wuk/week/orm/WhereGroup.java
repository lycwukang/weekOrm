package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
        return where.builderObj();
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