package com.Tsuda.springboot.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.Tsuda.springboot.Validation.BirthymdCheck;

public class BirthymdCheckValidator implements ConstraintValidator<BirthymdCheck, String>{

	private BirthymdCheck birthymdCheck;

	@Override
	public void initialize(BirthymdCheck birthymdCheck){
		this.birthymdCheck = birthymdCheck;
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt){
		String pattarn = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		if( input.matches(pattarn)){
			return true;
		}else{
			return false;
		}
	}

}
