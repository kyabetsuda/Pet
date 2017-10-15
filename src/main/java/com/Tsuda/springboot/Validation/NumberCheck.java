package com.Tsuda.springboot.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import com.Tsuda.springboot.Validator.NumberCheckValidator;

@Documented
@Constraint(validatedBy = NumberCheckValidator.class)
@Target({ ElementType.TYPE,ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface NumberCheck {
	
	String message() default "原価は数字を入力してください";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
