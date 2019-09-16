package com.example.controlleradvice.common.annotion;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface sysLog {
    String value() default "";
}
