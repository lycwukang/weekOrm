package wuk.week.orm;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WeekTable {

    /**
     * 表的名称
     * @return
     */
    String value() default "";
}