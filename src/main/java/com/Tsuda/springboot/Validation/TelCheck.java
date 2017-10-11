package com.Tsuda.springboot.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.Tsuda.springboot.Validator.TelCheckValidator;

@Documented
@Constraint(validatedBy = TelCheckValidator.class)
@Target({ ElementType.TYPE,ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface TelCheck {

	String message() default "電話番号は半角英数字14文字以内で入力してください";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
