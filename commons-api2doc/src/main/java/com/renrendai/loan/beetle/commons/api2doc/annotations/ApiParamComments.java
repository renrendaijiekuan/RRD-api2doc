package com.renrendai.loan.beetle.commons.api2doc.annotations;

import java.lang.annotation.*;

/**
 * Created by wudi on 2018/10/9
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiParamComments {
    ApiComment[] value();
}
