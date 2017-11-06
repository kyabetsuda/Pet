package com.Tsuda.springboot.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Service.ReserveTodayListJdbc;
import com.Tsuda.springboot.model.ReserveTodayListEntity;


@Controller
public class ReserveTodayController {
	
	@Autowired
	ReserveTodayListJdbc todlisjdbc;

	@RequestMapping(value ="/ReserveToday", method=RequestMethod.GET)
	public ModelAndView show(ModelAndView mav){
		mav.setViewName("ReserveToday");
		mav.addObject("msg","本日の予約");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date = sd.format(new Date());
		List<ReserveTodayListEntity> reservedStayed = todlisjdbc.findReservedStayed(date);
		List<ReserveTodayListEntity> reservedNotStayed = todlisjdbc.findReservedNotStayed(date);
		//部屋リストから予約されているものを抽出し、詳細を変更
		List<ReserveTodayListEntity> rooms = todlisjdbc.findAllRooms();
		for( ReserveTodayListEntity todaylist : rooms) {
			if( reservedStayed != null) {
				for( ReserveTodayListEntity reservedstayed : reservedStayed ) {
					if ( todaylist.getItemnm().equals(reservedstayed.getItemnm()) ) {
						todaylist.setReserved("有");
						todaylist.setStayed("宿泊中");
						todaylist.setCustomerid(reservedstayed.getCustomerid());
						todaylist.setCustomernm(reservedstayed.getCustomernm());
					}
				}
			}
			
			if( reservedNotStayed != null) {
				for( ReserveTodayListEntity reservednotstayed : reservedNotStayed ) {
					if ( todaylist.getItemnm().equals(reservednotstayed.getItemnm()) ) {
						todaylist.setReserved("有");
						todaylist.setStayed("空室");
						todaylist.setCustomerid(reservednotstayed.getCustomerid());
						todaylist.setCustomernm(reservednotstayed.getCustomernm());
					}
				}
			}
		}
		mav.addObject("datalist", rooms);
		return mav;
	}
	
	@RequestMapping(value ="/ReserveToday", method=RequestMethod.POST)
	public ModelAndView search(
			@RequestParam("itemnm") String itemnm,
			@RequestParam("reserved") String reserved,
			@RequestParam("stayed") String stayed,
			ModelAndView mav){
		mav.addObject("msg","本日の予約");
		List<ReserveTodayListEntity> todaylists = new ArrayList<ReserveTodayListEntity>();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date = sd.format(new Date());
		//ルーム番号を指定しなかった場合
		if( itemnm == null || itemnm.equals("")) {
			if( reserved.equals("Y") && stayed.equals("Y")) {
				todaylists = todlisjdbc.findReservedStayed(date);
			}else if(reserved.equals("Y") && stayed.equals("N")) {
				todaylists = todlisjdbc.findReservedNotStayed(date);
			}else if(reserved.equals("N")) {
				List<ReserveTodayListEntity> reservedStayed = todlisjdbc.findReservedStayed(date);
				List<ReserveTodayListEntity> reservedNotStayed = todlisjdbc.findReservedNotStayed(date);
				todaylists = todlisjdbc.findAllRooms();
				List<ReserveTodayListEntity> todaylists_copy = new ArrayList<ReserveTodayListEntity>(todaylists);
				for( ReserveTodayListEntity todaylist : todaylists_copy) {
					if( reservedStayed != null) {
						for( ReserveTodayListEntity reservedstayed : reservedStayed ) {
							if ( todaylist.getItemnm().equals(reservedstayed.getItemnm()) ) {
								todaylists.remove(todaylist);
							}
						}
					}
					
					if( reservedNotStayed != null) {
						for( ReserveTodayListEntity reservednotstayed : reservedNotStayed ) {
							if ( todaylist.getItemnm().equals(reservednotstayed.getItemnm()) ) {
								todaylists.remove(todaylist);
							}
						}
					}
				}
				
			}
		//ルーム番号を指定した場合
		}else {
			if( reserved.equals("Y") && stayed.equals("Y")) {
				todaylists = todlisjdbc.findReservedStayedByItemnm(date, itemnm);
			}else if(reserved.equals("Y") && stayed.equals("N")) {
				todaylists = todlisjdbc.findReservedNotStayedByItemnm(date, itemnm);
			}else if(reserved.equals("N")) {
				List<ReserveTodayListEntity> reservedStayed = todlisjdbc.findReservedStayed(date);
				List<ReserveTodayListEntity> reservedNotStayed = todlisjdbc.findReservedNotStayed(date);
				todaylists = todlisjdbc.findByItemnm(itemnm);
				List<ReserveTodayListEntity> todaylists_copy = new ArrayList<ReserveTodayListEntity>(todaylists);
				for( ReserveTodayListEntity todaylist : todaylists_copy) {
					if( reservedStayed != null) {
						for( ReserveTodayListEntity reservedstayed : reservedStayed ) {
							if ( todaylist.getItemnm().equals(reservedstayed.getItemnm()) ) {
								todaylists.remove(todaylist);
							}
						}
					}
					
					if( reservedNotStayed != null) {
						for( ReserveTodayListEntity reservednotstayed : reservedNotStayed ) {
							if ( todaylist.getItemnm().equals(reservednotstayed.getItemnm()) ) {
								todaylists.remove(todaylist);
							}
						}
					}
				}
				
			}
		}
		mav.addObject("datalist", todaylists);
		return mav;
	}
}
