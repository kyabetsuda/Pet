package com.Tsuda.springboot.Controller;

import java.text.SimpleDateFormat;
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
import com.Tsuda.springboot.Service.ReserveTodayList;
import com.Tsuda.springboot.model.Reserve;


@Controller
public class ReserveTodayController {
	
	@Autowired
	ReserveTodayList reserveList;
	
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
			@RequestParam("reserved") String reserved,
			@RequestParam("stayed") String stayed,
			ModelAndView mav){
		mav.addObject("msg","本日の予約");
		//検索条件にしたがってデータを抽出
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		List<Reserve> reserves = impl.getSearchResult(reserved, stayed, sd.format(new Date()));
		List<List<String>> datalist = reserveList.makeList(reserves);
		mav.addObject("datalist", datalist);
		return mav;
	}
}
