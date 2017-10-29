package com.Tsuda.springboot.Component;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.Repository.ReserveRepositoryCustom;
import com.Tsuda.springboot.model.Reserve;

@Repository
public class ReserveRepositoryCustomImpl implements ReserveRepositoryCustom{
	private static final long serialVersionUID = 1L;
	
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
				.createNativeQuery("select * from reserve where ((CHECK_IN_YMD <= :checkinymd) AND (CHECK_OUT_YMD between :checkinymd AND :checkoutymd)) OR ((CHECK_OUT_YMD >= :checkoutymd) AND (CHECK_IN_YMD between :checkinymd AND :checkoutymd)) OR ((CHECK_IN_YMD <= :checkinymd) AND (CHECK_OUT_YMD >= :checkoutymd))",Reserve.class)
				.setParameter("checkinymd",checkinymd)
				.setParameter("checkoutymd",checkoutymd)
				.getResultList();
		
		return reserves;
	}
	
	@Override
	public List<Reserve> getReservesToday(String date){
		List<Reserve> reserves = entityManager
				.createNativeQuery("select * from reserve where (CHECK_IN_YMD <= :date) AND (CHECK_OUT_YMD >= :date)", Reserve.class)
				.setParameter("date", date)
				.getResultList();
		
		return reserves;
	}
	
	@Override
	public List<Reserve> getSearchResult(String itemnm, String reserved, String stayed, String date){
		List<Reserve> reserves = null;
		if( (itemnm == null) || (itemnm == "")) {
			if( (reserved.equals("Y")) && (stayed.equals("Y")) )  {
	
			}else if( (reserved.equals("Y")) && (stayed.equals("N")) ) {
				
			}else if( (reserved.equals("N")) && (stayed.equals("Y")) ) {
				
			}else if( (reserved.equals("N")) && (stayed.equals("N")) ) {
				
			}
		}else {
			if( (reserved.equals("Y")) && (stayed.equals("Y")) )  {
				
			}else if( (reserved.equals("Y")) && (stayed.equals("N")) ) {
				
			}else if( (reserved.equals("N")) && (stayed.equals("Y")) ) {
				
			}else if( (reserved.equals("N")) && (stayed.equals("N")) ) {
				
			}
		}
		return reserves;
	}
	

}
