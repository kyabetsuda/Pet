package com.Tsuda.springboot.Controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Service.ReserveTodayListJdbc;
import com.Tsuda.springboot.Service.ReserveTodayListWithJdbc;
import com.Tsuda.springboot.model.ReserveTodayListEntity;


@Controller
public class ReserveTodayController {
	
	@Autowired
	ReserveTodayListJdbc todlisjdbc;
	
	@Autowired
	ReserveTodayListWithJdbc withJdbc;

	@RequestMapping(value ="/ReserveToday", method=RequestMethod.GET)
	public ModelAndView show(
			HttpServletRequest request,
			ModelAndView mav){
		mav.setViewName("ReserveToday");
		mav.addObject("msg","本日の予約");
		List<ReserveTodayListEntity> rooms = withJdbc.makeList();
		mav.addObject("datalist", rooms);
		
		HttpSession session = request.getSession(false);
		
		if( session == null) {
			session = request.getSession(true);
			session.removeAttribute("itemnm");
			session.removeAttribute("reserved");
			session.removeAttribute("stayed");
		}else {
			session.removeAttribute("itemnm");
			session.removeAttribute("reserved");
			session.removeAttribute("stayed");
		}
		
		return mav;
	}
	
	@RequestMapping(value ="/ReserveToday", method=RequestMethod.POST)
	public ModelAndView search(
			@RequestParam("itemnm") String itemnm,
			@RequestParam("reserved") String reserved,
			@RequestParam("stayed") String stayed,
			HttpServletRequest request,
			ModelAndView mav){
		mav.addObject("msg","本日の予約");
		List<ReserveTodayListEntity> todaylists = withJdbc.getSearchResult(itemnm, reserved, stayed);
		mav.addObject("datalist", todaylists);
		
		HttpSession session = request.getSession(false);
		
		if( session == null) {
			session = request.getSession(true);
			session.setAttribute("itemnm", itemnm);
			session.setAttribute("reserved", reserved);
			session.setAttribute("stayed", stayed);
		}else {
			session.setAttribute("itemnm", itemnm);
			session.setAttribute("reserved", reserved);
			session.setAttribute("stayed", stayed);
		}
		
		
		
		return mav;
	}

}
