package com.Tsuda.springboot.Controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Component.ItemRepositoryCustomImpl;
import com.Tsuda.springboot.Repository.ItemAttributeRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.ItemAttribute;

@Controller
public class ItemController {

	
	@Autowired
	ItemAttributeRepository repository;
	
	@Autowired
	ItemRepository itmRepository;
	
	@PersistenceContext
	EntityManager manager;

	@Autowired
	ItemRepositoryCustomImpl impl;
	
	@PostConstruct
	public void init() {
		impl = new ItemRepositoryCustomImpl(manager);
	}

	@RequestMapping(value ="/Item", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("Item");
		mav.addObject("msg","商品管理");
		List<ItemAttribute> itemAttributes = repository.findAll();
		List<Item> items = impl.getAllItems();
		mav.addObject("itemattributes", itemAttributes);
		mav.addObject("datalist", items);
		return mav;
	}

	@RequestMapping(value ="/Item", method=RequestMethod.POST)
	public ModelAndView search(
			@RequestParam("itemnm") String itemnm,
			@RequestParam("itemattribute") String itemattribute,
			ModelAndView mav){
		if( itemnm == "" ) {
			if( itemattribute.equals("all") ) {
				List<Item> items = itmRepository.findAll();
				mav.addObject("datalist", items);
			}else {
				int attribute = Integer.parseInt(itemattribute);
				List<Item> items = itmRepository.findByItemattribute(attribute);
				mav.addObject("datalist", items);
			}
		}else {
			if( itemattribute.equals("all") ) {
				List<Item> items = itmRepository.findByItemnm(itemnm);
				mav.addObject("datalist", items);
			}else {
				List<Item> items = impl.search(itemnm, itemattribute);
				mav.addObject("datalist", items);
			}
		}
		
		List<ItemAttribute> itemAttributes = repository.findAll();
		mav.addObject("msg","商品管理");
		mav.addObject("itemattributes", itemAttributes);
		return mav;


	}

}
