package com.Tsuda.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.UserRepository;
import com.Tsuda.springboot.model.User;

@Controller
public class UserRegisterController {
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping(value ="/UserRegister", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		
		mav.setViewName("UserRegister");
		mav.addObject("msg","ユーザー登録");
		mav.addObject("check", true);
		return mav;
	}
	
	@RequestMapping(value ="/UserRegister", method=RequestMethod.POST)
	public ModelAndView register(
			ModelAndView mav,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("confirmation") String confirmation){
		boolean flg = true;
		User user = new User();
		if ( username == null || username.equals("")) {
			mav.addObject("usernameNullCheck", true);
			flg = false;
		}
		
		if( password == null || password.equals("") ) {
			mav.addObject("passwordNullCheck", true);
			flg = false;
		}
		if( !password.equals(confirmation)) {
			mav.addObject("confirmationCheck", true);
			flg = false;
		}
		
		
		if( flg ) {
			user.setUsername(username);
			user.setPassword(password);
			repository.saveAndFlush(user);
			mav.addObject("msg","新規ユーザが登録されました");
			mav.addObject("check",false);
		}else {
			mav.addObject("msg","エラーが発生しました");
			mav.addObject("check",true);
		}
		return mav;
		
		
	}

}
