package com.Tsuda.springboot.model;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reserve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="RESERVE_ID")
	private int reserveid;
	
	@Column(name="START_YMD")
	private String startymd;
	
	@Column(name="END_YMD")
	private String endymd;
	
	@Column(name="CHECK_IN_YMD")
	private String checkinymd;
	
	@Column(name="CHECK_OUT_YMD")
	private String checkoutymd;
	
	@Column(name="SELL_NO")
	private int sellno;
	
	@Column(name="INS_YMD", insertable = false, updatable = false)
	private Timestamp insymd;

	@Column(name="UPD_YMD", insertable = false, updatable = true)
	private Timestamp updymd;
	
	public void setReserveid(int reserveid) {
		this.reserveid = reserveid;
	}
	
	public int getReserveid() {
		return reserveid;
	}
	
	public void setStartymd(String startymd) {
		this.startymd = startymd;
	}
	
	public String getStartymd() {
		return startymd;
	}
	
	public void setEndymd(String endymd) {
		this.endymd = endymd;
	}
	
	public String getEndymd() {
		return endymd;
	}
	
	public void setCheckinymd(String checkinymd) {
		this.checkinymd = checkinymd;
	}
	
	public String getCheckinymd() {
		return checkinymd;
	}
	
	public void setCheckoutymd(String checkoutymd) {
		this.checkoutymd = checkoutymd;
	}
	
	public String getCheckoutymd() {
		return checkoutymd;
	}
	
	public void setSellno(int sellno) {
		this.sellno = sellno;
	}
	
	public int getSellno() {
		return sellno;
	}
	
	public void setInsymd(Timestamp insymd){
		this.insymd = insymd;
	}

	public Timestamp getInsymd(){
		return insymd;
	}

	public void setUpdymd(Timestamp updymd){
		this.updymd = updymd;
	}

	public Timestamp getUpdymd(){
		return updymd;
	}
	
	
	
	
	
	
}
