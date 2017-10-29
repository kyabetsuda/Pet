package com.Tsuda.springboot.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.model.Customer;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;

@Service
public class ReserveStatusCheck {
	
	@Autowired
	ReserveRepository resrepository;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	ItemRepository itmrepository;
	
	@Autowired
	CustomerRepository cstrepository;
	
	public List<String> makeReserveStatus(int reserveid){
		Reserve reserve = resrepository.findByReserveid(reserveid);
		Sell sell = selrepository.findBySellno(reserve.getSellno());
		Item item = itmrepository.findByItemcd(sell.getItemcd());
		Customer customer = cstrepository.findByCustomerId(sell.getCustomerid());
		List<String> data = new ArrayList<String>();
		data.add(String.valueOf(reserve.getReserveid()));
		data.add(reserve.getStartymd());
		data.add(reserve.getEndymd());
		data.add(reserve.getCheckinymd());
		data.add(reserve.getCheckoutymd());
		data.add(item.getItemnm());
		data.add(String.valueOf(customer.getCustomerId()));
		data.add(customer.getCustomername());
		data.add(sell.getPrice());
		data.add(sell.getTax());
		data.add(sell.getIncludingtax());
		return data;
	}
	
	public ModelAndView check(
			ModelAndView mav,
			int reserveid,
			String checkinymd,
			String checkoutymd) {
		boolean flg = true;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		//チェックイン日チェック
		if( (checkinymd == null) || (checkinymd == "")) {
			mav.addObject("checkinymdNullCheck", true);
			flg = false;
		}
		String pattarn = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		if( !checkinymd.matches(pattarn)){
			mav.addObject("checkinymdPattarnCheck", true);
			flg = false;
		}
		
		//チェックアウト日チェック
		if( (checkoutymd == null) || (checkoutymd == "")) {
			mav.addObject("checkoutymdNullCheck", true);
			flg = false;
		}
		if( !checkoutymd.matches(pattarn)){
			mav.addObject("checkoutymdPattarnCheck", true);
			flg = false;
		}
		
		try {
			Date checkinymd_date = sd.parse(checkinymd);
			Date checkoutymd_date = sd.parse(checkoutymd);
			int diff = checkinymd_date.compareTo(checkoutymd_date);
			if( diff >= 0 ) {
				mav.addObject("checkoutymdBeforeCheckinymd", true);
				flg = false;
			}
		}catch(ParseException e) {
			mav.addObject("checkoutymdPattarnCheck",true);
			flg = false;
		}
		
		if ( flg ) {
			mav.addObject("msg","詳細が更新されました");
			mav.addObject("check",false);
			Reserve reserve = resrepository.findByReserveid(reserveid);
			reserve.setCheckinymd(checkinymd);
			reserve.setCheckoutymd(checkoutymd);
			reserve.setEndymd(checkoutymd);
			reserve.setUpdymd(new Timestamp(System.currentTimeMillis()));
			resrepository.saveAndFlush(reserve);	
		}else {
			mav.addObject("msg","エラーが発生しました");
			mav.addObject("check", true);
			List<String> data = makeReserveStatus(reserveid);
			mav.addObject("data", data);
		}
		
		return mav;
	}

}
