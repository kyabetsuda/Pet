package com.Tsuda.springboot.Validator;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.Tsuda.springboot.Validation.NumberCheck;

public class NumberCheckValidator implements ConstraintValidator<NumberCheck, String>{

	private NumberCheck numberCheck;
	
	@Override
	public void initialize(NumberCheck numberCheck){
		this.numberCheck = numberCheck;
	}
	
	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt){
		try {
	        BigDecimal big = new BigDecimal(input);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
}
