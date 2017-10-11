package com.Tsuda.springboot.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.Tsuda.springboot.Validator.BirthymdCheckValidator;

@Documented
@Constraint(validatedBy = BirthymdCheckValidator.class)
@Target({ ElementType.TYPE,ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface BirthymdCheck {

	String message() default "生年月日の形式が違います";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
