package com.Tsuda.springboot.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;

import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveRepository;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;

@Service
public class MakeSell {
	
	@Autowired
	ItemRepository itmrepository;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	ReserveRepository resrepository;
	
	EntityManager entityManager;

	public MakeSell(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	public void makeSell(
			int customerid,
			String date,
			String itemcd,
			int quantity
			) {
		Sell sell = new Sell();
		sell.setSellymd(new Date());
		sell.setCustomerid(customerid);
		sell.setItemcd(itemcd);
		sell.setQuantity(quantity);
		Item item = itmrepository.findByItemcd(itemcd);
		BigDecimal price_per_one = new BigDecimal( item.getItemprice() );
		BigDecimal quantity_big = new BigDecimal( String.valueOf(quantity));
		BigDecimal tax_rate = new BigDecimal( "0.08");
		BigDecimal price = price_per_one.multiply(quantity_big);
		BigDecimal tax = price.multiply(tax_rate);
		BigDecimal including_tax = price.add(tax);
		sell.setPrice(price.toString());
		sell.setIncludingtax(including_tax.toString());
		sell.setTax(tax.toString());
		selrepository.saveAndFlush(sell);
	}
	
	public void makeReserve(String date, int quantity) {
		Reserve reserve = new Reserve();
		Date checkinymd = null;
		Date checkout = null;
		String checkoutymd = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			checkinymd = sd.parse(date);
		} catch (ParseException e) {
            e.printStackTrace();
        }
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(checkinymd);
		calendar.add(Calendar.DATE, quantity);
		checkout = (Date) calendar.getTime();
		checkoutymd = sd.format(checkout);
		reserve.setStartymd(date);
		reserve.setEndymd(checkoutymd);
		Sell sell = (Sell) entityManager
				.createNativeQuery("select * from sell order by SELL_NO desc limit 1", Sell.class)
				.getSingleResult();
		reserve.setSellno(sell.getSellno());
		resrepository.saveAndFlush(reserve);
	}

}
