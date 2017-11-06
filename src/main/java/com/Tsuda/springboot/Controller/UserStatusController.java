package com.Tsuda.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.UserRepository;
import com.Tsuda.springboot.model.User;

@Controller
public class UserStatusController {
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping(value ="/UserStatus/{id}", method=RequestMethod.GET)
	public ModelAndView show(
			@PathVariable("id") int id,
			ModelAndView mav){
		
		mav.setViewName("UserStatus");
		mav.addObject("msg","ユーザー詳細");
		mav.addObject("check", true);
		User user = repository.findByUserid(id);
		mav.addObject("user",user);
		return mav;
	}
	
	@RequestMapping(value ="/UserStatus", method=RequestMethod.POST)
	public ModelAndView register(
			ModelAndView mav,
			@RequestParam("userid") int userid,
			@RequestParam("username") String username,
			@RequestParam("password") String password){
		boolean flg = true;
		if ( username == null || username.equals("")) {
			mav.addObject("usernameNullCheck", true);
			flg = false;
		}
		
		if( password == null || password.equals("") ) {
			mav.addObject("passwordNullCheck", true);
			flg = false;
		}
		
		
		if( flg ) {
			User user = repository.findByUserid(userid);
			user.setUsername(username);
			user.setPassword(password);
			repository.saveAndFlush(user);
			mav.addObject("msg","ユーザー情報が登録されました");
			mav.addObject("check",false);
		}else {
			mav.addObject("msg","エラーが発生しました");
			User user = repository.findByUserid(userid);
			mav.addObject("user",user);
			mav.addObject("check",true);
		}
		return mav;
	}

}
