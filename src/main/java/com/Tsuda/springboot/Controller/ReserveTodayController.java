package com.Tsuda.springboot.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Component.ReserveRepositoryCustomImpl;
import com.Tsuda.springboot.Component.SellRepositoryCustomImpl;
import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.model.Customer;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;

@Controller
public class ReserveTodayController {
	
	@Autowired
	ItemRepository itmrepository;
	
	@Autowired
	ReserveRepository resrepository;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	CustomerRepository cstrepository;
	
	@Autowired
	ReserveRepositoryCustomImpl impl;
	
	@Autowired
	SellRepositoryCustomImpl selimpl;
	
	@Autowired
	EntityManager entityManager;
	
	@PostConstruct
	public void init() {
		impl = new ReserveRepositoryCustomImpl(entityManager);
	}

	@RequestMapping(value ="/ReserveToday", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("ReserveToday");
		mav.addObject("msg","本日の予約");
		List<List<String>> datalist = new ArrayList<List<String>>();
		List<Item> items = itmrepository.findByItemattribute(2);
		//宿泊中、または本日予約が入っているreserveを取り出す
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		List<Reserve> reserves = impl.getReservesToday(sd.format(new Date()));
		
		//reservesから予約の入っている部屋リストを取得
		List<Sell> sells = new ArrayList<Sell>();
		for( Reserve reserve : reserves) {
			sells.add(selrepository.findBySellno(reserve.getSellno()));
		}
		List<Item> reserved = new ArrayList<Item>();
		for( Sell sell : sells) {
			reserved.add(itmrepository.findByItemcd(sell.getItemcd()));
		}
		
		//datalist作成
		for( Item item : items) {
			List<String> data = new ArrayList<String>();
			//ルーム番号追加
			data.add(item.getItemnm());
			//もしreservedがitemを含んでいれば、予約有りを追加
			if( reserved.contains(item)) {
				data.add("有");
				data.add("宿泊中");
				//item.getItemcd()から本日予約のsellを割り出し、顧客情報を取得
				List<Sell> sellsByItemcd = selrepository.findByItemcd(item.getItemcd());
				for ( Sell sell : sellsByItemcd) {
					if(sells.contains(sell)) {
						Customer customer = cstrepository.findByCustomerId(sell.getCustomerid());
						data.add(String.valueOf(customer.getCustomerId()));
						data.add(customer.getCustomername());
					}else {
						continue;
					}
				}
			}else{
				data.add("無");
				data.add("");
				data.add("");
				data.add("");
			}
			
			//datalistにdataを追加
			datalist.add(data);
		}
		
		
		mav.addObject("datalist",datalist);
		return mav;
	}
}
