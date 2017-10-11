package com.Tsuda.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.model.Customer;

@Controller
public class CustomerStatusController {

	@Autowired
	CustomerRepository repository;

	@RequestMapping(value ="/CustomerStatus/{id}", method=RequestMethod.GET)
	public ModelAndView show(
			@PathVariable("id") int id,
			ModelAndView mav){
		mav.setViewName("CustomerStatus");
		Customer customer = repository.findByCustomerId(id);
		mav.addObject("msg","顧客編集");
		mav.addObject("customer",customer);
		return mav;

	}

	@RequestMapping(value ="/CustomerStatus", method=RequestMethod.POST)
	public ModelAndView register(
			@ModelAttribute("customer")
			@Validated Customer customer,
			BindingResult result,
			ModelAndView mav){
		if ( !result.hasErrors() ){
			repository.saveAndFlush(customer);
			mav.addObject("msg","顧客情報が登録されました。");
		}else{
			mav.addObject("msg","エラーが発生しました。");
		}
		return mav;
	}

}
