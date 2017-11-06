package com.Tsuda.springboot.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Tsuda.springboot.model.ReserveTodayListEntity;


@Service
public class ReserveTodayListJdbc {
	
	public List<ReserveTodayListEntity> findReservedStayed(String date){
		List<ReserveTodayListEntity> todaylists = new ArrayList<ReserveTodayListEntity>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =
	                DriverManager.getConnection("jdbc:mysql://localhost/petbooking?"+
	                                            "user=root&password=root");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select i.ITEM_NM as itemnm, s.CUSTOMER_ID as customerid, c.CUSTOMER_NM as customernm"
					+ " from reserve r"
					+ " inner join sell s on r.SELL_NO = s.SELL_NO"
					+ " left join customer c on s.CUSTOMER_ID = c.CUSTOMER_ID"
					+ " left join item i on s.ITEM_CD = i.ITEM_CD"
					+ " where (r.START_YMD <= '" + date + "') AND (r.END_YMD >= '" + date + "') AND (r.CHECK_IN_YMD IS NOT NULL)");
			while( result.next() ) {
				ReserveTodayListEntity todaylist = new ReserveTodayListEntity();
				todaylist.setItemnm(result.getString("itemnm"));
				todaylist.setReserved("有");
				todaylist.setStayed("宿泊中");
				todaylist.setCustomerid(String.valueOf(result.getInt("customerid")));
				todaylist.setCustomernm(result.getString("customernm"));
				todaylists.add(todaylist);
			}
			
			
		}catch ( Exception e) {
			
		}
		
		return todaylists;
	}
	
	public List<ReserveTodayListEntity> findReservedStayedByItemnm(String date, String itemnm){
		List<ReserveTodayListEntity> todaylists = new ArrayList<ReserveTodayListEntity>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =
	                DriverManager.getConnection("jdbc:mysql://localhost/petbooking?"+
	                                            "user=root&password=root");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select i.ITEM_NM as itemnm, s.CUSTOMER_ID as customerid, c.CUSTOMER_NM as customernm"
					+ " from reserve r"
					+ " inner join sell s on r.SELL_NO = s.SELL_NO"
					+ " left join customer c on s.CUSTOMER_ID = c.CUSTOMER_ID"
					+ " left join item i on s.ITEM_CD = i.ITEM_CD"
					+ " where (r.START_YMD <= '" + date + "') AND (r.END_YMD >= '" + date + "') AND (r.CHECK_IN_YMD IS NOT NULL) AND (i.ITEM_NM = '" + itemnm + "')");
			while( result.next() ) {
				ReserveTodayListEntity todaylist = new ReserveTodayListEntity();
				todaylist.setItemnm(result.getString("itemnm"));
				todaylist.setReserved("有");
				todaylist.setStayed("宿泊中");
				todaylist.setCustomerid(String.valueOf(result.getInt("customerid")));
				todaylist.setCustomernm(result.getString("customernm"));
				todaylists.add(todaylist);
			}
			
			
		}catch ( Exception e) {
			
		}
		
		return todaylists;
	}

	public List<ReserveTodayListEntity> findReservedNotStayed(String date){
		List<ReserveTodayListEntity> todaylists = new ArrayList<ReserveTodayListEntity>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =
	                DriverManager.getConnection("jdbc:mysql://localhost/petbooking?"+
	                                            "user=root&password=root");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select i.ITEM_NM as itemnm, s.CUSTOMER_ID as customerid, c.CUSTOMER_NM as customernm"
					+ " from reserve r"
					+ " inner join sell s on r.SELL_NO = s.SELL_NO"
					+ " left join customer c on s.CUSTOMER_ID = c.CUSTOMER_ID"
					+ " left join item i on s.ITEM_CD = i.ITEM_CD"
					+ " where (r.START_YMD <= '" + date + "') AND (r.END_YMD >= '" + date + "') AND (r.CHECK_IN_YMD IS NULL)");
			while( result.next() ) {
				ReserveTodayListEntity todaylist = new ReserveTodayListEntity();
				todaylist.setItemnm(result.getString("itemnm"));
				todaylist.setReserved("有");
				todaylist.setStayed("空室");
				todaylist.setCustomerid(String.valueOf(result.getInt("customerid")));
				todaylist.setCustomernm(result.getString("customernm"));
				todaylists.add(todaylist);
			}
			
			
		}catch ( Exception e) {
			
		}
		
		return todaylists;
	}
	
	public List<ReserveTodayListEntity> findReservedNotStayedByItemnm(String date, String itemnm){
		List<ReserveTodayListEntity> todaylists = new ArrayList<ReserveTodayListEntity>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =
	                DriverManager.getConnection("jdbc:mysql://localhost/petbooking?"+
	                                            "user=root&password=root");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select i.ITEM_NM as itemnm, s.CUSTOMER_ID as customerid, c.CUSTOMER_NM as customernm"
					+ " from reserve r"
					+ " inner join sell s on r.SELL_NO = s.SELL_NO"
					+ " left join customer c on s.CUSTOMER_ID = c.CUSTOMER_ID"
					+ " left join item i on s.ITEM_CD = i.ITEM_CD"
					+ " where (r.START_YMD <= '" + date + "') AND (r.END_YMD >= '" + date + "') AND (r.CHECK_IN_YMD IS NULL) AND (i.ITEM_NM = '" + itemnm + "')");
			while( result.next() ) {
				ReserveTodayListEntity todaylist = new ReserveTodayListEntity();
				todaylist.setItemnm(result.getString("itemnm"));
				todaylist.setReserved("有");
				todaylist.setStayed("空室");
				todaylist.setCustomerid(String.valueOf(result.getInt("customerid")));
				todaylist.setCustomernm(result.getString("customernm"));
				todaylists.add(todaylist);
			}
			
			
		}catch ( Exception e) {
			
		}
		
		return todaylists;
	}
	
	public List<ReserveTodayListEntity> findAllRooms(){
		List<ReserveTodayListEntity> todaylists = new ArrayList<ReserveTodayListEntity>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =
	                DriverManager.getConnection("jdbc:mysql://localhost/petbooking?"+
	                                            "user=root&password=root");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select i.ITEM_NM as itemnm from item i where ITEM_ATTRIBUTE = 2");
			while( result.next() ) {
				ReserveTodayListEntity todaylist = new ReserveTodayListEntity();
				todaylist.setItemnm(result.getString("itemnm"));
				todaylist.setReserved("無");
				todaylist.setStayed("");
				todaylist.setCustomerid("");
				todaylist.setCustomernm("");
				todaylists.add(todaylist);
			}
			
			
		}catch ( Exception e) {
			
		}
		
		return todaylists;
	}
	
	public List<ReserveTodayListEntity> findByItemnm(String itemnm){
		List<ReserveTodayListEntity> todaylists = new ArrayList<ReserveTodayListEntity>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =
	                DriverManager.getConnection("jdbc:mysql://localhost/petbooking?"+
	                                            "user=root&password=root");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select i.ITEM_NM as itemnm from item i where (i.ITEM_ATTRIBUTE = 2) AND (i.ITEM_NM = '"+ itemnm +"')");
			while( result.next() ) {
				ReserveTodayListEntity todaylist = new ReserveTodayListEntity();
				todaylist.setItemnm(result.getString("itemnm"));
				todaylist.setReserved("無");
				todaylist.setStayed("");
				todaylist.setCustomerid("");
				todaylist.setCustomernm("");
				todaylists.add(todaylist);
			}
			
			
		}catch ( Exception e) {
			
		}
		
		return todaylists;
	}

}
