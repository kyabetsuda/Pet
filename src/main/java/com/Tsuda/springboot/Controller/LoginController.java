package com.Tsuda.springboot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(value ="/login", method=RequestMethod.GET)
	public ModelAndView login(ModelAndView mav){
		mav.setViewName("login");
		mav.addObject("msg","必要項目を入力してログインしてください。");
		return mav;
	}

}
