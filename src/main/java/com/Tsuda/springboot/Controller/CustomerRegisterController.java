package com.Tsuda.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.model.Customer;

@Controller
public class CustomerRegisterController {

	@Autowired
	CustomerRepository repository;

	@RequestMapping(value ="/CustomerRegister", method=RequestMethod.GET)
	public ModelAndView show(
			ModelAndView mav){
		mav.setViewName("CustomerRegister");
		Customer customer = new Customer();
		mav.addObject("msg","顧客登録");
		mav.addObject("customer", customer);
		return mav;
	}

	@RequestMapping(value ="/CustomerRegister", method=RequestMethod.POST)
	public ModelAndView register(
			@ModelAttribute("customer")
			@Validated Customer customer,
			BindingResult result,
			ModelAndView mav){
		if( !result.hasErrors() ){
			repository.saveAndFlush(customer);
			mav.addObject("msg","新規顧客が登録されました。");
		}else{
			mav.addObject("msg", "エラーが発生しました。");
		}

		return mav;
	}

}
