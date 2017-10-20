package com.Tsuda.springboot.Controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Component.ItemRepositoryCustomImpl;
import com.Tsuda.springboot.Component.ReserveRepositoryCustomImpl;
import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.model.Customer;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;

@Controller
public class ReserveRegisterController {
	
	@Autowired
	CustomerRepository repository;
	
	@Autowired
	ItemRepository itmrepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ReserveRepositoryCustomImpl impl;
	
	@Autowired
	SellRepository selrepository;
	
	@PostConstruct
	public void init() {
		impl = new ReserveRepositoryCustomImpl(entityManager);
	}
	
	@RequestMapping(value ="/ReserveRegister", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("ReserveRegister");
		mav.addObject("msg","予約入力");
		List<Customer> customers = repository.findAll();
		mav.addObject("customers",customers);
		return mav;
		
		
	}
//	@RequestMapping(value="/ReserveRegister/{checkinymd}/{num}", method=RequestMethod.POST)
//	@ResponseBody
//	public void unko(
//			@PathVariable("checkinymd") Date checkinymd,
//			@PathVariable("num") int num
//			) {
//			return;
//	}
	
	@RequestMapping(value = "/ReserveRegister/{checkinymd}/{num}", method=RequestMethod.POST)
	@ResponseBody
	public String roomList(
			@PathVariable("checkinymd") Date checkinymd,
			@PathVariable("num") int num){
		List<Item> items = itmrepository.findByItemattribute(2);
		//予約できない部屋を取り出す
//		List<Reserve> reserves = impl.getReserves(checkinymd, num);
		//予約リストをアイテムリストに変換する
//		List<Sell> sells = new ArrayList<Sell>();
//		for( Reserve reserve : reserves) {
//			sells.add(selrepository.findBySellno(reserve.getSellno()));
//		}
//		List<Item> reserved = new ArrayList<Item>();
//		for( Sell sell : sells) {
//			reserved.add(itmrepository.findByItemcd(sell.getItemcd()));
//		}
		//itemsから予約できない部屋を取り除く
//		for( Item item : items) {
//			for( Item reservedItem : reserved) {
//				if( item.equals(reservedItem)) {
//					items.remove(reservedItem);
//				}else{
//					continue;
//				}
//			}
//		}
		
		//itemsをhtmlに変換
		StringBuilder sb = new StringBuilder("<option value=\"nothing\">-</option>");

        items.stream()
            .map(value -> String.format("<option value=\"%s\">%s</option>", value.getItemnm(), value.getItemnm()))
            .forEach(option -> sb.append(option));
		return sb.toString();
		
	} 

}
