package com.Tsuda.springboot.Controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.ItemAttributeRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.ItemAttribute;

@Controller
public class ItemStatusController {
	
	@Autowired
	ItemRepository repository;
	
	@Autowired
	ItemAttributeRepository attrepository;

	@RequestMapping(value ="/ItemStatus/{itemcd}", method=RequestMethod.GET)
	public ModelAndView show(
			@PathVariable("itemcd") String itemcd,
			ModelAndView mav){
		mav.setViewName("ItemStatus");
		mav.addObject("msg","商品編集");
		Item item = repository.findByItemcd(itemcd);
		mav.addObject("item", item);
		List<ItemAttribute> attributes = attrepository.findAll();
		mav.addObject("attributes", attributes);
		
		return mav;
	}
	
	@RequestMapping(value ="/ItemStatus", method=RequestMethod.POST)
	public ModelAndView register(
			@ModelAttribute("item")
			@Validated Item item,
			BindingResult result,
			ModelAndView mav) {
		if( !result.hasErrors() ) {
			item.setUpdymd(new Timestamp(System.currentTimeMillis()));
			repository.saveAndFlush(item);
			mav.addObject("msg","商品情報が登録されました。");
		}else {
			mav.addObject("msg","エラーが発生しました");
		}
		List<ItemAttribute> attributes = attrepository.findAll();
		mav.addObject("attributes", attributes);
		return mav;
	}
	
}
