package com.Tsuda.springboot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.UserRepository;
import com.Tsuda.springboot.model.User;

@Controller
public class UserController {
	
	@Autowired
	UserRepository repository;

	@RequestMapping(value ="/User", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("User");
		List<User> users = repository.findAll();
		mav.addObject("msg","ユーザ管理");
		mav.addObject("datalist",users);
		return mav;
		
	}
	
}
