package com.Tsuda.springboot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Service.ReserveStatusCheck;

@Controller
public class ReserveStatusController {
	
	@Autowired
	ReserveStatusCheck statusCheck;
	
	@RequestMapping(value="ReserveStatus/{id}", method=RequestMethod.GET)
	public ModelAndView show(
			@PathVariable("id") int reserveid,
			ModelAndView mav) {
		mav.setViewName("ReserveStatus");
		mav.addObject("check",true);
		List<String> data = statusCheck.makeReserveStatus(reserveid);
		mav.addObject("data",data);
		mav.addObject("msg","予約詳細");
		return mav;
	}
	
	@RequestMapping(value="ReserveStatus", method=RequestMethod.POST)
	public ModelAndView update(
			@RequestParam("reserveid") int reserveid,
			@RequestParam("checkinymd") String checkinymd,
			@RequestParam("checkoutymd") String checkoutymd,
			ModelAndView mav) {
		mav = statusCheck.check(mav, reserveid, checkinymd, checkoutymd);
		return mav;
	}
			

}
