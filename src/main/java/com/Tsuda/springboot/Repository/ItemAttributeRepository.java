package com.Tsuda.springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.ItemAttribute;

@Repository
public interface ItemAttributeRepository extends JpaRepository<ItemAttribute, Integer>{

	public ItemAttribute findByItemattributeno(int Itemattributeno);

}
