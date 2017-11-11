package com.Tsuda.springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.Reserve;
import com.Tsuda.springboot.model.ReserveList;

@Repository
public interface ReserveListRepository extends JpaRepository<ReserveList, Integer>{
	
	@Query(value ="select r.RESERVE_ID as reserveid, s.CUSTOMER_ID as customerid, i.ITEM_NM as itemnm, r.START_YMD as startymd, r.END_YMD as endymd"
			+ " from reserve r"
			+ " inner join sell s on r.SELL_NO = s.SELL_NO"
			+ " left join item i on s.ITEM_CD = i.ITEM_CD"
			+ " order by reserveid",
			nativeQuery = true)
	public List<ReserveList> findAll();

}
