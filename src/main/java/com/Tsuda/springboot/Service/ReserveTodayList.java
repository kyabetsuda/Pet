package com.Tsuda.springboot.Service;

import java.util.ArrayList;
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
		return datalist;
	}

}
