package com.Tsuda.springboot.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.Tsuda.springboot.Validator.PostcdCheckValidator;



@Documented
@Constraint(validatedBy = PostcdCheckValidator.class)
@Target({ ElementType.TYPE,ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface PostcdCheck {


	String message() default "郵便番号は半角数字7文字以下で入力してください";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
