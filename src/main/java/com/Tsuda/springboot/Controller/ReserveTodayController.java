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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Component.ReserveRepositoryCustomImpl;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.Service.ReserveTodayList;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;


@Controller
public class ReserveTodayController {
	
	@Autowired
	ReserveTodayList reserveList;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	ItemRepository itmrepository;
	
	@Autowired
	ReserveRepositoryCustomImpl impl;
	
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
		//宿泊中、または本日予約が入っているreserveを取り出す
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		List<Reserve> reserves = impl.getReservesToday(sd.format(new Date()));
		List<List<String>> datalist = reserveList.makeList(reserves);
		mav.addObject("datalist",datalist);
		return mav;
	}
	
	@RequestMapping(value ="/ReserveToday", method=RequestMethod.POST)
	public ModelAndView search(
			@RequestParam("itemnm") String itemnm,
			@RequestParam("reserved") String reserved,
			@RequestParam("stayed") String stayed,
			ModelAndView mav){
		mav.addObject("msg","本日の予約");
		//検索条件にしたがってデータを抽出
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date = sd.format(new Date());
		List<List<String>> datalist = new ArrayList<List<String>>();
		List<Reserve> reserves = null;
		if( reserved.equals("Y") && stayed.equals("Y")) {
			reserves = impl.getReservedStayed(date);
			datalist = reserveList.makeSearchList(reserves, itemnm);
		}else if(reserved.equals("Y") && stayed.equals("N")) {
			reserves = impl.getReservedNotStayed(date);
			datalist = reserveList.makeSearchList(reserves, itemnm);
		}else if(reserved.equals("N")) {
			datalist = reserveList.makeNotReservedItemList(impl, itemnm);
		}
		mav.addObject("datalist", datalist);
		return mav;
	}
}
