package com.Tsuda.springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ReserveCheck {
	
	@Autowired
	MakeSell makeSell;

	public ModelAndView reserveCheck(
			int customerid,
			String date,
			String itemcd,
			String quantity,
			ModelAndView mav
			) {
		boolean flg = true;
		//顧客IDチェック
		try {
			Integer check = new Integer(customerid);
		}catch (NumberFormatException e) {
			mav.addObject("customerIdNullCheck", true);
			flg = false;
		}
		
		//日付チェック
		if( (date == null) || (date == "")) {
			mav.addObject("dateNullCheck", true);
			flg = false;
		}
		String pattarn = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		if( !date.matches(pattarn)){
			mav.addObject("datePattarnCheck", true);
			flg = false;
		}
		
		//宿泊日数チェック
		try {
			Integer check = new Integer(quantity);
		}catch (NumberFormatException e) {
			mav.addObject("quantityNullCheck", true);
			flg = false;
		}
		
		//アイテムコードチェック
		if( (itemcd == null) || (itemcd =="")) {
			mav.addObject("itemcdNullCheck", true);
			flg = false;
		}
		
		if( flg ) {
			int quantityTranslated = Integer.valueOf(quantity);
			makeSell.makeSell(customerid, date, itemcd, quantityTranslated);
			makeSell.makeReserve(date, quantityTranslated);
			mav.addObject("msg","予約が登録されました");
			mav.addObject("check",false);
		}
		
		return mav;
	}
	
}
