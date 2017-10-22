package com.Tsuda.springboot.model;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Sell {
	
	@Id
	@Column(name="SELL_NO")
	private int sellno;
	
	@Column(name="SELL_YMD")
	private Date sellymd;
	
	@Column(name="CUSTOMER_ID")
	private int customerid;
	
	@Column(name="ITEM_CD")
	private String itemcd;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	@Column(name="PRICE")
	private String price;
	
	@Column(name="INCLUDING_TAX")
	private String includingtax;
	
	@Column(name="TAX")
	private String tax;
	
	@Column(name="INS_YMD", insertable = false, updatable = false)
	private Timestamp insymd;

	@Column(name="UPD_YMD", insertable = false, updatable = true)
	private Timestamp updymd;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SELL_NO", referencedColumnName="SELL_NO")
	private Reserve reserve;
	
	public void setSellno(int sellno) {
		this.sellno = sellno;
	}
	
	public int getSellno() {
		return sellno;
	}
	
	public void setSellymd(Date sellymd) {
		this.sellymd = sellymd;
	}
	
	public Date getSellymd() {
		return sellymd;
	}
	
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	
	public int getCustomerid() {
		return customerid;
	}
	
	public void setItemcd(String itemcd) {
		this.itemcd = itemcd;
	}
	
	public String getItemcd() {
		return itemcd;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setIncludingtax(String includingtax) {
		this.includingtax = includingtax;
	}
	
	public String getIncludingtax() {
		return includingtax;
	}
	
	public void setTax(String tax) {
		this.tax = tax;
	}
	
	public String getTax() {
		return tax;
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
	
	public Reserve getReserve() {
		return reserve;
	}
}
