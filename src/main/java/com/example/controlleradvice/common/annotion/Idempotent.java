package com.example.controlleradvice.common.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Idempotent {
    /**
     * 幂等key的分组值，例如：sale-check
     * @return
     */
    String value();

    /**
     * 参数的表达式，用来确定key值，例如："#req.saleInfo.channelCode+'-'+#req.seqId"
     * @return
     */
    String express();
}
