package com.Tsuda.springboot.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.Tsuda.springboot.Validation.EmailCheck;

public class EmailCheckValidator implements ConstraintValidator<EmailCheck, String>{

	private EmailCheck emailCheck;

	@Override
	public void initialize(EmailCheck emailCheck){
		this.emailCheck = emailCheck;
	}

	@Override
	public boolean isValid(String address, ConstraintValidatorContext cxt){
		boolean result;
		String aText = "[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]";
		String dotAtom = aText + "+" + "(\\." + aText + "+)*";
		String regularExpression = "^" + dotAtom + "@" + dotAtom + "$";
		result = checkMailAddress(address,regularExpression);
//		User user = repository.findByMail(address);
//		if( user != null ){
//			return false;
//		}
		return result;

	}

	private static boolean checkMailAddress(String address, String regularExpression) {
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(address);
		if ( matcher.find()) {
			return true;
		}
		return false;
	}

}
