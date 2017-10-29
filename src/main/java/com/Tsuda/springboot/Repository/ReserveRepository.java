package com.Tsuda.springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.Customer;
import com.Tsuda.springboot.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer>{

	public List<Reserve> findAll();
	public Reserve findByReserveid(int reserveid);
	public List<Reserve> findByCheckinymd(String checkinymd);
	
}
