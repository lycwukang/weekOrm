package wuk.week.orm;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WeekColumn {

    /**
     * 数据库字段名称
     * @return
     */
    String value() default "";

    /**
     * 是否是自增长键
     * @return
     */
    boolean autoIncrement() default false;

    /**
     * 是否是主键
     * @return
     */
    boolean primaryKey() default false;

    /**
     * 字段是否不能为空
     * @return
     */
    boolean notNull() default true;

    /**
     * 是否有默认值
     * @return
     */
    boolean defaultValue() default false;
}