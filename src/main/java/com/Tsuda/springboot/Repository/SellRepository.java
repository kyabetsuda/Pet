package com.Tsuda.springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.Sell;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer>{
	
	public Sell findBySellno(int sellno);
	public List<Sell> findByItemcd(String itemcd);

}
