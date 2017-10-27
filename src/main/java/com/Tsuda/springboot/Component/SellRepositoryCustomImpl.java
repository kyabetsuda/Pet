package com.Tsuda.springboot.Component;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.Repository.SellRepositoryCustom;
import com.Tsuda.springboot.model.Sell;

@Repository
public class SellRepositoryCustomImpl implements SellRepositoryCustom{
private static final long serialVersionUID = 1L;
	
	EntityManager entityManager;

	public SellRepositoryCustomImpl(){
		super();
	}

	public SellRepositoryCustomImpl(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	
	
	
}
