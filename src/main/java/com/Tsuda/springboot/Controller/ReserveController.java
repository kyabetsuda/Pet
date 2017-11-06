package com.Tsuda.springboot.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveListRepository;
import com.Tsuda.springboot.Repository.ReserveRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.ReserveList;
import com.Tsuda.springboot.model.Sell;

@Controller
public class ReserveController {
	
	@Autowired
	ReserveRepository resrepository;
	
	@Autowired
	ReserveListRepository lisrepository;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	ItemRepository itmrepository;
	
	@RequestMapping(value ="/Reserve", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("Reserve");
		mav.addObject("msg","予約一覧");
		List<ReserveList> reserveList = lisrepository.findAll();		
		mav.addObject("datalist",reserveList);
		return mav;
	}

}
