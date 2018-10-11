package com.renrendai.loan.beetle.commons.api2doc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Repeatable(ApiErrors.class)
public @interface ApiError {

	/**
	 * 错误码。
	 *
	 * @return
	 */
	String value();

	/**
	 * 错误码的说明。
	 *
	 * @return
	 */
	String comment() default "";
}
