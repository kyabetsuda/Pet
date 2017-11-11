package com.Tsuda.springboot.Controller;




import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Service.GetExcel;
import com.Tsuda.springboot.Service.ReserveTodayListWithJdbc;
import com.Tsuda.springboot.model.ReserveTodayListEntity;


@Controller
public class ExcelTodayController {
	
	@Autowired
	ReserveTodayListWithJdbc withJdbc;
	
	@Autowired
	GetExcel getExcel;

	@RequestMapping(value ="/ExcelToday", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		
		mav.setViewName("ExcelToday");
		return mav;
	}
	
	@RequestMapping(value ="/ExcelToday", method=RequestMethod.POST)
	public void getExcel(
			HttpServletRequest request,
			HttpServletResponse response			
			) throws FileNotFoundException, IOException{
		HttpSession session = request.getSession(false);
		List<ReserveTodayListEntity> rooms = new ArrayList<ReserveTodayListEntity>();
		
		String itemnm = null;
		String reserved = null;
		String stayed = null;
		if( session == null) {
			session = request.getSession(true);
			itemnm = (String)session.getAttribute("itemnm");
			reserved = (String)session.getAttribute("reserved");
			stayed = (String)session.getAttribute("stayed");
		}else {
			itemnm = (String)session.getAttribute("itemnm");
			reserved = (String)session.getAttribute("reserved");
			stayed = (String)session.getAttribute("stayed");
		}
		
		if( reserved == null ) {
			rooms = withJdbc.makeList();
		}else {
			rooms = withJdbc.getSearchResult(itemnm, reserved, stayed);
		}
		
		getExcel.getExcel(request, response, rooms);
		
	}
	
}
