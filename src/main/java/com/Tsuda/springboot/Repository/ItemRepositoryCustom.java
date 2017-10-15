package com.Tsuda.springboot.Repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.Item;

@Repository
public interface ItemRepositoryCustom extends Serializable{

	
	public List<Item> getAllItems();
	public List<Item> search(String itemnm, String itemattribute);
}
