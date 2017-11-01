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
import com.Tsuda.springboot.Service.ReserveComparator;
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
	
	@Autowired
	ItemRepository itmrepository;
	
	@Autowired
	SellRepository selrepository;
	
	@Autowired
	CustomerRepository cstrepository;
	
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
				.createNativeQuery("select * from reserve where ( (DATE(CHECK_IN_YMD) <= :checkinymd) AND (DATE(CHECK_OUT_YMD) between :checkinymd AND :checkoutymd) ) OR ( (DATE(CHECK_OUT_YMD) >= :checkoutymd) AND ( DATE(CHECK_IN_YMD) between :checkinymd AND :checkoutymd)) OR ( ( DATE(CHECK_IN_YMD) <= :checkinymd) AND ( DATE(CHECK_OUT_YMD) >= :checkoutymd))",Reserve.class)
				.setParameter("checkinymd",checkinymd)
				.setParameter("checkoutymd",checkoutymd)
				.getResultList();
		
		return reserves;
	}
	
	@Override
	public List<Reserve> getReservesToday(String date){
		//予約されている部屋のリスト
		List<Reserve> reserves = entityManager
				.createNativeQuery("select * from reserve where (DATE(CHECK_IN_YMD) <= :date) AND (DATE(CHECK_OUT_YMD) >= :date)", Reserve.class)
				.setParameter("date", date)
				.getResultList();
		
		return reserves;
	}
	
	@Override
	public List<Reserve> getSearchResult(String itemnm, String reserved, String stayed, String date){
		List<Reserve> reserves = null;
		if( (itemnm == null) || (itemnm == "")) {
			if( (reserved.equals("Y")) && (stayed.equals("Y")) )  {
				reserves = entityManager
						.createNativeQuery("select * from reserve where (CHECK_IN_YMD <= :date) AND (CHECK_OUT_YMD >= :date)", Reserve.class)
						.setParameter("date", date)
						.getResultList();
			}else if( (reserved.equals("Y")) && (stayed.equals("N")) ) {
				reserves = entityManager
						.createNativeQuery("select * from reserve where (DATE(CHECK_IN_YMD) <= :date) AND (CHECK_IN_YMD > :date)", Reserve.class)
						.setParameter("date", date)
						.getResultList();
			}else if( (reserved.equals("N")) && (stayed.equals("Y")) ) {
				
			}else if( (reserved.equals("N")) && (stayed.equals("N")) ) {
				reserves = entityManager
						.createNativeQuery("select * from reserve where (DATE(CHECK_IN_YMD) > :date) AND (CHECK_OUT_YMD < :date)", Reserve.class)
						.setParameter("date", date)
						.getResultList();
			}
		}else {
			if( (reserved.equals("Y")) && (stayed.equals("Y")) )  {
				reserves = entityManager
						.createNativeQuery("select * from reserve where (CHECK_IN_YMD <= :date) AND (CHECK_OUT_YMD >= :date) AND (ITEM_NM = :itemnm)", Reserve.class)
						.setParameter("date", date)
						.setParameter("itemnm", itemnm)
						.getResultList();
			}else if( (reserved.equals("Y")) && (stayed.equals("N")) ) {
				reserves = entityManager
						.createNativeQuery("select * from reserve where (DATE(CHECK_IN_YMD) <= :date) AND (CHECK_IN_YMD > :date) AND (ITEM_NM = :itemnm)", Reserve.class)
						.setParameter("date", date)
						.setParameter("itemnm", itemnm)
						.getResultList();
			}else if( (reserved.equals("N")) && (stayed.equals("Y")) ) {
				
			}else if( (reserved.equals("N")) && (stayed.equals("N")) ) {
				reserves = entityManager
						.createNativeQuery("select * from reserve where (DATE(CHECK_IN_YMD) > :date) AND (CHECK_OUT_YMD < :date) AND (ITEM_NM = :itemnm)", Reserve.class)
						.setParameter("date", date)
						.setParameter("itemnm", itemnm)
						.getResultList();
			}
		}
		return reserves;
	}
	

}
