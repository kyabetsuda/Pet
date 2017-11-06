package com.Tsuda.springboot.model;

import javax.persistence.Entity;
import javax.persistence.Id;


public class ReserveTodayListEntity {
	
	private String itemnm;
	private String reserved;
	private String stayed;
	private String customerid;
	private String customernm;
	
	public void setItemnm(String itemnm) {
		this.itemnm = itemnm;
	}
	public String getItemnm() {
		return itemnm;
	}
	
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getReserved() {
		return reserved;
	}
	
	public void setStayed(String stayed) {
		this.stayed = stayed;
	}
	public String getStayed() {
		return stayed;
	}
	
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getCustomerid() {
		return customerid;
	}
	
	public void setCustomernm(String customernm) {
		this.customernm = customernm;
	}
	public String getCustomernm() {
		return customernm;
	}

}
