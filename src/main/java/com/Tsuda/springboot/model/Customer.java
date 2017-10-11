package com.Tsuda.springboot.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.Tsuda.springboot.Validation.BirthymdCheck;
import com.Tsuda.springboot.Validation.EmailCheck;
import com.Tsuda.springboot.Validation.PostcdCheck;
import com.Tsuda.springboot.Validation.TelCheck;

@Entity
@Table(name="customer")
public class Customer {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CUSTOMER_ID",nullable=false)
	private int customerId;

	@Column(name="CUSTOMER_NM",nullable=false)
	@NotEmpty(message = "顧客名を入力してください")
	private String customername;

	@Column(name="CUSTOMER_KN",nullable=false)
	@NotEmpty(message = "顧客名かなを入力してください")
	private String customerkn;

	@Column(name="SEX")
	private String sex;

	@Column(name="POST_CD")
	@PostcdCheck
	private String postcd;

	@Column(name="ADDRESS1")
	private String address1;

	@Column(name="ADDRESS2")
	private String address2;

	@Column(name="ADDRESS3")
	private String address3;

	@Column(name="ADDRESS4")
	private String address4;

	@Column(name="TEL")
	@TelCheck
	private String tel;

	@Column(name="EMAIL")
	@EmailCheck
	private String email;

	@Column(name="BIRTH_YMD")
	@BirthymdCheck
	private String birthymd;


	@Column(name="INS_YMD")
	private Timestamp insymd = new Timestamp(System.currentTimeMillis());

	@Column(name="UPD_YMD")
	private Timestamp updymd = new Timestamp(System.currentTimeMillis());


	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}

	public int getCustomerId(){
		return customerId;
	}

	public void setCustomername(String customername){
		this.customername = customername;
	}

	public String getCustomername(){
		return customername;
	}

	public void setCustomerkn(String customerkn){
		this.customerkn = customerkn;
	}

	public String getCustomerkn(){
		return customerkn;
	}

	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return sex;
	}

	public void setPostcd(String postcd){
		this.postcd = postcd;
	}

	public String getPostcd(){
		return postcd;
	}

	public void setAddress1(String address1){
		this.address1 = address1;
	}

	public String getAddress1(){
		return address1;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return address2;
	}

	public void setAddress3(String address3){
		this.address3 = address3;
	}

	public String getAddress3(){
		return address3;
	}

	public void setAddress4(String address4){
		this.address4 = address4;
	}

	public String getAddress4(){
		return address4;
	}

	public void setTel(String tel){
		this.tel = tel;
	}

	public String getTel(){
		return tel;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setBirthymd(String birthymd){
		this.birthymd = birthymd;
	}

	public String getBirthymd(){
		return birthymd;
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
