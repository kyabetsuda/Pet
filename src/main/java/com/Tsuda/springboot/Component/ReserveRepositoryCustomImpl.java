package com.Tsuda.springboot.Component;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.Tsuda.springboot.Repository.CustomerRepository;
import com.Tsuda.springboot.Repository.ItemRepository;
import com.Tsuda.springboot.Repository.ReserveRepositoryCustom;
import com.Tsuda.springboot.Repository.SellRepository;
import com.Tsuda.springboot.Service.ReserveTodayList;
import com.Tsuda.springboot.model.Customer;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.Sell;

@Service
public class ReserveRepositoryCustomImpl implements ReserveRepositoryCustom{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ReserveTodayList  reserveList;
	
	EntityManager entityManager;

	public ReserveRepositoryCustomImpl(){
		super();
	}

	public ReserveRepositoryCustomImpl(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Reserve> getReserves(String date, int num){
		Date checkinymd = null;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	        checkinymd = sd.parse(date);
		} catch (ParseException e) {
            e.printStackTrace();
        }
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(checkinymd);
		calendar.add(Calendar.DATE, num);
		Date checkoutymd = (Date) calendar.getTime();
		List<Reserve> reserves = entityManager
				.createNativeQuery("select * from reserve where ( (START_YMD <= :checkinymd) AND ( END_YMD between :checkinymd AND :checkoutymd) ) OR ( ( END_YMD >= :checkoutymd ) AND ( START_YMD between :checkinymd AND :checkoutymd)) OR ( ( START_YMD <= :checkinymd) AND ( END_YMD >= :checkoutymd))",Reserve.class)
				.setParameter("checkinymd",checkinymd)
				.setParameter("checkoutymd",checkoutymd)
				.getResultList();
		
		return reserves;
	}
	
	@Override
	public List<Reserve> getReservesToday(String date){
		//予約されている部屋のリスト
		List<Reserve> reserves = entityManager
				.createNativeQuery("select * from reserve where (START_YMD <= :date) AND (END_YMD >= :date)", Reserve.class)
				.setParameter("date", date)
				.getResultList();
		
		return reserves;
	}
	
	@Override
	public List<Reserve> getReservedStayed(String date){
		List<Reserve> reserves  = entityManager
						.createNativeQuery("select * from reserve where ( START_YMD <= :date ) AND ( END_YMD >= :date ) AND ( CHECK_IN_YMD IS NOT NULL)", Reserve.class)
						.setParameter("date", date)
						.getResultList();

		return reserves;
	}
	
	@Override
	public List<Reserve> getReservedNotStayed(String date){
		List<Reserve> reserves  = entityManager
						.createNativeQuery("select * from reserve where ( START_YMD <= :date ) AND ( END_YMD >= :date ) AND ( CHECK_IN_YMD IS NULL)", Reserve.class)
						.setParameter("date", date)
						.getResultList();

		return reserves;
	}
	

}
