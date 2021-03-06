package com.Tsuda.springboot.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Component.ReserveRepositoryCustomImpl;
import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.Service.MakeSell;
import com.Tsuda.springboot.Service.ReserveCheck;
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
	ReserveRepository resrepository;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	ReserveCheck reserveCheck;
	
	@PostConstruct
	public void init() {
		impl = new ReserveRepositoryCustomImpl(entityManager);
	}
	
	@RequestMapping(value ="/ReserveRegister", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav) throws JSONException{
		mav.setViewName("ReserveRegister");
		mav.addObject("msg","予約入力");
		mav.addObject("check",true);
		List<Customer> customers = repository.findAll();
		mav.addObject("customers",customers);
		return mav;
		
		
	}
	
	@RequestMapping(value = "/ReserveRegister", method=RequestMethod.POST)
	public ModelAndView register(
			@RequestParam("customerid") int customerid,
			@RequestParam("checkinymd") String date,
			@RequestParam("itemcd") String itemcd,
			@RequestParam("quantity") String quantity,
			ModelAndView mav
			) {
		mav.addObject("msg","予約入力");
		List<Customer> customers = repository.findAll();
		mav.addObject("customers",customers);
		mav.addObject("check",true);
		mav = reserveCheck.reserveCheck(customerid, date, itemcd, quantity, mav);
		return mav;
		
		
	}
	
	
	@RequestMapping(value = "/roomlist/{checkinymd}/{num}", method=RequestMethod.POST)
	@ResponseBody
	public String roomList(
			@PathVariable("checkinymd") String checkinymd,
			@PathVariable("num") int num) throws JSONException{
		List<Item> items = itmrepository.findByItemattribute(2);
		//予約できない部屋を取り出す
		List<Reserve> reserves = impl.getReserves(checkinymd, num);
		//予約リストをアイテムリストに変換する
		List<Sell> sells = new ArrayList<Sell>();
		for( Reserve reserve : reserves) {
			sells.add(selrepository.findBySellno(reserve.getSellno()));
		}
		List<Item> reserved = new ArrayList<Item>();
		for( Sell sell : sells) {
			reserved.add(itmrepository.findByItemcd(sell.getItemcd()));
		}
		//itemsから予約できない部屋を取り除く
		for( Item reservedItem : reserved) {
			if( items.contains(reservedItem)) {
				items.remove(reservedItem);
			}else{
				continue;
			}
		}
		
		JSONObject datalist = new JSONObject();
		JSONArray status = new JSONArray();
		
		for(Item item : items) {
			JSONObject data = new JSONObject();
			data.put("itemcd",item.getItemcd());
			data.put("itemnm", item.getItemnm());
			status.put(data);
		}

	    return status.toString();
		
	} 

}
