package com.Tsuda.springboot.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;

@Controller
public class ReserveController {
	
	@Autowired
	ReserveRepository resrepository;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	ItemRepository itmrepository;
	
	@RequestMapping(value ="/Reserve", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("Reserve");
		mav.addObject("msg","予約一覧");
		List<List<String>> datalist = new ArrayList<List<String>>();
		//必要なリストを取得
		List<Reserve> reserves = resrepository.findAll();
		
		//dataをdatalistに詰めていく
		for( Reserve reserve : reserves) {
			List<String> data = new ArrayList<String>();
			//予約ID
			data.add(String.valueOf(reserve.getReserveid()));
			//顧客ID
			Sell sell = selrepository.findBySellno(reserve.getSellno());
			data.add(String.valueOf(sell.getCustomerid()));
			//ルーム番号
			Item item = itmrepository.findByItemcd(sell.getItemcd());
			data.add(item.getItemnm());
			//予約開始日、終了日
			data.add(reserve.getStartymd().toString());
			data.add(reserve.getEndymd());
			//datalistに追加
			datalist.add(data);
		}
		mav.addObject("datalist",datalist);
		
		return mav;
	}

}
