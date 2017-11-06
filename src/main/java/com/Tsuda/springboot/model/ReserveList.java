package com.Tsuda.springboot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReserveList {
	
	@Id
	private int reserveid;
	private int customerid;
	private String itemnm;
	private String startymd;
	private String endymd;
	
	public int getReserveid() {
		return reserveid;
	}
	
	public int getCustomerid() {
		return customerid;
	}
	
	public String getItemnm() {
		return itemnm;
	}
	
	public String getStartymd() {
		return startymd;
	}
	
	public String getEndymd() {
		return endymd;
	}

}
