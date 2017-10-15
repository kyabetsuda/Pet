package com.Tsuda.springboot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.ItemAttributeRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.ItemAttribute;

@Controller
public class ItemRegisterController {

	@Autowired
	ItemRepository repository;

	@Autowired
	ItemAttributeRepository attrepository;

	@RequestMapping(value ="/ItemRegister", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("ItemRegister");
		Item item = new Item();
		List<ItemAttribute> attributes = attrepository.findAll();
		mav.addObject("attributes", attributes);
		mav.addObject("item",item);
		mav.addObject("attributes", attributes);
		mav.addObject("msg","商品登録");
		return mav;
	}

	@RequestMapping(value ="/ItemRegister", method=RequestMethod.POST)
	public ModelAndView register(
			@ModelAttribute("item")
			@Validated Item item,
			BindingResult result,
			ModelAndView mav){
		if( !result.hasErrors() ){
			repository.saveAndFlush(item);
//			ItemAttribute attribute = attrepository.findByItemattributeno(item.getItemattribute());
//			attribute.addItem(item);
//			final SessionFactory sessionFactory = getSessionFactory();
//			Session session = sessionFactory.openSession();
//			Transaction tx = session.beginTransaction();
//			attrepository.saveAndFlush(attribute);
			mav.addObject("msg","新規商品が登録されました。");
		}else{
			mav.addObject("msg", "エラーが発生しました。");
		}
		
		List<ItemAttribute> attributes = attrepository.findAll();
		mav.addObject("attributes", attributes);
		return mav;
	}

}
