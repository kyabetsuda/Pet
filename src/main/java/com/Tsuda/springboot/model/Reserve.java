package com.Tsuda.springboot.model;

import java.sql.Date;
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
	private Date startymd;
	
	@Column(name="END_YMD")
	private Date endymd;
	
	@Column(name="CHECK_IN_YMD")
	private Date checkinymd;
	
	@Column(name="CHECK_OUT_YMD")
	private Date checkoutymd;
	
	@Column(name="SELL_NO")
	private int sellno;
	
	@Column(name="INS_YMD")
	private Timestamp insymd;
	
	@Column(name="UPD_YMD")
	private Timestamp updymd;
	
	public void setReserveid(int reserveid) {
		this.reserveid = reserveid;
	}
	
	public int getReserveid() {
		return reserveid;
	}
	
	public void setStartymd(Date startymd) {
		this.startymd = startymd;
	}
	
	public Date getStartymd() {
		return startymd;
	}
	
	public void setEndymd(Date endymd) {
		this.endymd = endymd;
	}
	
	public Date getEndymd() {
		return endymd;
	}
	
	public void setCheckinymd(Date checkinymd) {
		this.checkinymd = checkinymd;
	}
	
	public Date getCheckinymd() {
		return checkinymd;
	}
	
	public void setCheckoutymd(Date checkoutymd) {
		this.checkoutymd = checkoutymd;
	}
	
	public Date getCheckoutymd() {
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
