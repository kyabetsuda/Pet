package com.Tsuda.springboot.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tsuda.springboot.Component.ReserveRepositoryCustomImpl;
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
		if( reserves != null) {
				for( Reserve reserve : reserves) {
					String checkinymd = reserve.getCheckinymd();
					if( (checkinymd != null) ) {
						sells1.add(selrepository.findBySellno(reserve.getSellno()));
					}else {
						sells2.add(selrepository.findBySellno(reserve.getSellno()));
					}
				}
			
		}
		
		if( sells1 != null ) { 
			for( Sell sell : sells1) {
				reserved1.add(itmrepository.findByItemcd(sell.getItemcd()));
			}
		}
		
		if( sells2 != null) {
			for( Sell sell : sells2) {
				reserved2.add(itmrepository.findByItemcd(sell.getItemcd()));
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
	
	public List<List<String>> makeSearchList(List<Reserve> reserves, String itemnm){
		List<List<String>> datalist = new ArrayList<List<String>>();
		List<Sell> sells1 = new ArrayList<Sell>();
		List<Item> reserved1 = new ArrayList<Item>();
		List<Sell> sells2 = new ArrayList<Sell>();
		List<Item> reserved2 = new ArrayList<Item>();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date = sd.format(new Date());
		Date dateToday = null;
		Date startymd = null;
		Date endymd = null;
		if( reserves != null) {
			for( Reserve reserve : reserves ) {
				try {
					dateToday = sd.parse(date);
					startymd = sd.parse(reserve.getStartymd());
					endymd = sd.parse(reserve.getEndymd());
				}catch( Exception e) {
					
				}
				
				if( ( dateToday.compareTo(startymd) >= 0 ) && ( dateToday.compareTo(endymd) <= 0) && ( reserve.getCheckinymd() != null ) ) {
					sells1.add(selrepository.findBySellno(reserve.getSellno()));
				}else if (( dateToday.compareTo(startymd) >= 0 ) && ( dateToday.compareTo(endymd) <= 0) && ( reserve.getCheckinymd() == null ) ) {
					sells2.add(selrepository.findBySellno(reserve.getSellno()));
				}else {
					
				}
			}
		
		}
		
		if( sells1 != null ) { 
			for( Sell sell : sells1) {
				reserved1.add(itmrepository.findByItemcd(sell.getItemcd()));
			}
		}
		
		if( sells2 != null) {
			for( Sell sell : sells2) {
				reserved2.add(itmrepository.findByItemcd(sell.getItemcd()));
			}
		}
		
		if( itemnm == null || itemnm.equals("")) {
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
						if(sells2.contains(sell)) {
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
		}else {
			if( reserved1 != null ) {
				for( Item item : reserved1 ) {
					if( item.getItemnm().equals(itemnm) ) {
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
					}else {
						continue;
					}
				}
			}
			
			if( reserved2 != null) {
				for( Item item : reserved2 ) {
					if( item.getItemnm().equals(itemnm) ) {
						List<String> data = new ArrayList<String>();
						data.add(item.getItemnm());
						data.add("有");
						data.add("空室");
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
						
						datalist.add(data);
					}else {
						continue;
					}
				}
			}
			
		}
		
		
		
		return datalist;
		
		
	}
	
	public List<List<String>> makeNotReservedItemList(ReserveRepositoryCustomImpl impl, String itemnm){
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date = sd.format(new Date());
		List<Reserve> reserves = impl.getReservesToday(date);
		List<Sell> sells = new ArrayList<Sell>();
		List<Item> items = itmrepository.findByItemattribute(2);
		List<Item> reservedItem = new ArrayList<Item>();
		List<List<String>> datalist = new ArrayList<List<String>>();
		if( reserves != null) {
			for( Reserve reserve: reserves) {
				sells.add(selrepository.findBySellno(reserve.getSellno()));
			}
			for( Sell sell : sells) {
				reservedItem.add(itmrepository.findByItemcd(sell.getItemcd()));
			}
		}
		
		for( int i = 0; i < items.size(); i++) {
			if( reservedItem.contains(items.get(i))) {
				items.remove(items.get(i));
			}
		}
		if( itemnm == null || itemnm.equals("")) {
			for( Item item : items ) {
				List<String> data = new ArrayList<String>();
				data.add(item.getItemnm());
				data.add("無");
				data.add("");
				data.add("");
				data.add("");
				
				datalist.add(data);
			}
		}else {
			for( Item item : items ) {
				if( item.getItemnm().equals(itemnm)) {
					List<String> data = new ArrayList<String>();
					data.add(item.getItemnm());
					data.add("無");
					data.add("");
					data.add("");
					data.add("");
					
					datalist.add(data);
				}else {
					continue;
				}
			}
		}
		
		return datalist;
	}
	

}
