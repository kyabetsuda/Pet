package com.Tsuda.springboot.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.Tsuda.springboot.Validation.PostcdCheck;

public class PostcdCheckValidator implements ConstraintValidator<PostcdCheck, String>{

	private PostcdCheck postcdCheck;

	@Override
	public void initialize(PostcdCheck postcdCheck){
		this.postcdCheck = postcdCheck;
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt){
		if( input.length() > 7 ){
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
