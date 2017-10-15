package com.Tsuda.springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>{

	public List<Item> findAll();
	public List<Item> findByItemattribute(int itemattribute);
	public Item findByItemcd(String itemcd);
	public List<Item> findByItemnm(String itemnm);
	public List<Item> findByItemattribute(String itemattribute);

}
