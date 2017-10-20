package com.Tsuda.springboot.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.Reserve;

@Repository
public interface ReserveRepositoryCustom extends Serializable{
	
	public List<Reserve> getReserves(Date checkinymd, int num);

}
