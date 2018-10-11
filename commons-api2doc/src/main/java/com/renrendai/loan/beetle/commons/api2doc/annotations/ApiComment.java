package com.renrendai.loan.beetle.commons.api2doc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
		ElementType.TYPE,
		ElementType.METHOD,
		ElementType.PARAMETER,
		ElementType.FIELD,
})
public @interface ApiComment {

	String name() default "";

	String value() default "";

	String sample() default "";

	/**
	 * 采用指定类的同名字段上的说明信息
	 *
	 * @return
	 */
	Class<?> seeClass() default Object.class;

	String seeField() default "";

}
