package com.Tsuda.springboot.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.Tsuda.springboot.Validator.EmailCheckValidator;

@Documented
@Constraint(validatedBy = EmailCheckValidator.class)
@Target({ ElementType.TYPE,ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface EmailCheck {

	String message() default "EMAILの形式が違います";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
