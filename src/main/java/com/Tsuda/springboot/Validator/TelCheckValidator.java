package com.Tsuda.springboot.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.Tsuda.springboot.Validation.TelCheck;

public class TelCheckValidator implements ConstraintValidator<TelCheck, String>{


	private TelCheck telCheck;

	@Override
	public void initialize(TelCheck telCheck){
		this.telCheck = telCheck;
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt){
		if( input.length() > 14 ){
			return false;
		}

		try {
	        Integer.parseInt(input);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }

	}

}
