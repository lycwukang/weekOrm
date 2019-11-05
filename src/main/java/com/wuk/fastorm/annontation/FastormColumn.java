package com.wuk.fastorm.annontation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastormColumn {

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
}
