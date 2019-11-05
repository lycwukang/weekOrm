package com.wuk.fastorm.annontation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastormTable {

    /**
     * 表名
     * @return
     */
    String value() default "";

    /**
     * 表所在的schema名称
     * @return
     */
    String schema() default "";
}
