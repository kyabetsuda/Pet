package com.Tsuda.springboot.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.model.Customer;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;

@Service
public class ReserveTodayList {
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	ItemRepository itmrepository;
	
	@Autowired
	CustomerRepository cstrepository;
	
	public List<List<String>> makeList(List<Reserve> reserves){
		List<List<String>> datalist = new ArrayList<List<String>>();
		List<Item> items = itmrepository.findByItemattribute(2);
		List<Sell> sells1 = new ArrayList<Sell>();
		List<Sell> sells2 = new ArrayList<Sell>();
		List<Item> reserved1 = new ArrayList<Item>();
		List<Item> reserved2 = new ArrayList<Item>();
		Date date1 = null;
		Date date2 = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateToday = sd.format(new Date());
		if( reserves != null) {
				for( Reserve reserve : reserves) {
					try {
						date1 = sd.parse(dateToday);
						date2 = sd.parse(reserve.getCheckinymd());
					}catch( Exception e) {
						
					}
					if( date1.compareTo(date2) >= 0 ) {
						sells1.add(selrepository.findBySellno(reserve.getSellno()));
						for( Sell sell : sells1) {
							reserved1.add(itmrepository.findByItemcd(sell.getItemcd()));
						}
					}else {
						sells2.add(selrepository.findBySellno(reserve.getSellno()));
						for( Sell sell : sells2) {
							reserved2.add(itmrepository.findByItemcd(sell.getItemcd()));
						}
					}
				}
			
		}
		//datalist作成
		for( Item item : items) {
			List<String> data = new ArrayList<String>();
			//ルーム番号追加
			data.add(item.getItemnm());
			//もしreserved1がitemを含んでいれば、予約有り,空室を追加
			if( reserved1.contains(item)) {
				data.add("有");
				data.add("宿泊中");
				//item.getItemcd()から本日予約のsellを割り出し、顧客情報を取得
				List<Sell> sellsByItemcd = selrepository.findByItemcd(item.getItemcd());
				for ( Sell sell : sellsByItemcd) {
					if(sells1.contains(sell)) {
						Customer customer = cstrepository.findByCustomerId(sell.getCustomerid());
						data.add(String.valueOf(customer.getCustomerId()));
						data.add(customer.getCustomername());
					}else {
						continue;
					}
				}
			//もしreserved2がitemを含んでいれば、予約有り、宿泊中を追加
			}else if( reserved2.contains(item)) {
				data.add("有");
				data.add("空室");
				//item.getItemcd()から本日予約のsellを割り出し、顧客情報を取得
				List<Sell> sellsByItemcd = selrepository.findByItemcd(item.getItemcd());
				for ( Sell sell : sellsByItemcd) {
					if(sells2.contains(sell)) {
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
		
		return datalist;
	}
	
	public List<List<String>> makeSearchList(List<Reserve> reserves){
		List<List<String>> datalist = new ArrayList<List<String>>();
		List<Item> items = itmrepository.findByItemattribute(2);
		List<Sell> sells1 = new ArrayList<Sell>();
		List<Sell> sells2 = new ArrayList<Sell>();
		List<Item> reserved1 = new ArrayList<Item>();
		List<Item> reserved2 = new ArrayList<Item>();
		Date date1 = null;
		Date date2 = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateToday = sd.format(new Date());
		if( reserves != null) {
				for( Reserve reserve : reserves) {
					try {
						date1 = sd.parse(dateToday);
						date2 = sd.parse(reserve.getCheckinymd());
					}catch( Exception e) {
						
					}
					if( date1.compareTo(date2) >= 0 ) {
						sells1.add(selrepository.findBySellno(reserve.getSellno()));
						for( Sell sell : sells1) {
							reserved1.add(itmrepository.findByItemcd(sell.getItemcd()));
						}
					}else {
						sells2.add(selrepository.findBySellno(reserve.getSellno()));
						for( Sell sell : sells2) {
							reserved2.add(itmrepository.findByItemcd(sell.getItemcd()));
						}
					}
				}
			
		}
		
		if( reserved1 != null ) {
			for( Item item : reserved1 ) {
				List<String> data = new ArrayList<String>();
				data.add(item.getItemnm());
				data.add("有");
				data.add("宿泊中");
				List<Sell> sellsByItemcd = selrepository.findByItemcd(item.getItemcd());
				for ( Sell sell : sellsByItemcd) {
					if(sells1.contains(sell)) {
						Customer customer = cstrepository.findByCustomerId(sell.getCustomerid());
						data.add(String.valueOf(customer.getCustomerId()));
						data.add(customer.getCustomername());
					}else {
						continue;
					}
				}
				
				datalist.add(data);
			}
		}
		
		if( reserved2 != null) {
			for( Item item : reserved2 ) {
				List<String> data = new ArrayList<String>();
				data.add(item.getItemnm());
				data.add("有");
				data.add("空室");
				List<Sell> sellsByItemcd = selrepository.findByItemcd(item.getItemcd());
				for ( Sell sell : sellsByItemcd) {
					if(sells1.contains(sell)) {
						Customer customer = cstrepository.findByCustomerId(sell.getCustomerid());
						data.add(String.valueOf(customer.getCustomerId()));
						data.add(customer.getCustomername());
					}else {
						continue;
					}
				}
				
				datalist.add(data);
			}
		}
		
		return datalist;
		
		
	}

}
