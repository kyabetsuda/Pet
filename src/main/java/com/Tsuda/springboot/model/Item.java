package com.Tsuda.springboot.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.Tsuda.springboot.Validation.NumberCheck;

@Entity
@Table(name="item")
public class Item {

	@Id
	@Column(name="ITEM_CD")
	@NotEmpty(message="商品コードを入力してください")
	private String itemcd;

	@Column(name="ITEM_NM")
	@NotEmpty(message="商品名を入力してください")
	private String itemnm;

	@Column(name="ITEM_ATTRIBUTE")
	private int itemattribute;

	@Column(name="ITEM_COST")
	@NotEmpty(message="原価を入力してください")
	@NumberCheck
	private String itemcost;

	@Column(name="ITEM_PRICE")
	@NotEmpty(message="売値を入力してください")
	@NumberCheck(message="売値は数字で入力してください")
	private String itemprice;

	@Column(name="INS_YMD", insertable = false, updatable = false)
	private Timestamp insymd;

	@Column(name="UPD_YMD", insertable = false, updatable = true)
	private Timestamp updymd;

	public void setItemcd(String itemcd){
		this.itemcd = itemcd;
	}

	public String getItemcd(){
		return itemcd;
	}

	public void setItemnm(String itemnm){
		this.itemnm = itemnm;
	}

	public String getItemnm(){
		return itemnm;
	}

	public void setItemattribute(int itemattribute){
		this.itemattribute = itemattribute;
	}

	public int getItemattribute(){
		return itemattribute;
	}

	public void setItemcost(String itemcost){
		this.itemcost = itemcost;
	}

	public String getItemcost(){
		return itemcost;
	}

	public void setItemprice(String itemprice){
		this.itemprice = itemprice;
	}

	public String getItemprice(){
		return itemprice;
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
