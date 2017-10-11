package com.Tsuda.springboot.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TopController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(
			HttpServletRequest request,
			ModelAndView mav){
		mav.setViewName("top");

		HttpSession session = request.getSession(false);

		if(session == null){
			mav = new ModelAndView("redirect:/login");
		}else{
			mav.addObject("msg","トップページ");
		}
		return mav;
	}

}
