package com.Tsuda.springboot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.model.Customer;

@Controller
public class CustomerController {

	@Autowired
	CustomerRepository repository;

	@RequestMapping(value ="/Customer", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("Customer");
		mav.addObject("msg","顧客管理");
		List<Customer> customers = repository.findAll();
		mav.addObject("datalist",customers);
		return mav;
	}

	@RequestMapping(value ="/Customer", method=RequestMethod.POST)
	public ModelAndView search(
			@RequestParam("customername") String customername,
			ModelAndView mav){
		List<Customer> customer = repository.findByCustomername(customername);
		mav.addObject("datalist", customer);


		return mav;
	}
}
