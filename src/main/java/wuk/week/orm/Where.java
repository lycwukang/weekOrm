package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.List;

public interface Where {

    void builder(StringBuilder builder);

    List<Pair<Field, Object>> builderObj();

    Where and(Where where);

    Where or(Where where);
}