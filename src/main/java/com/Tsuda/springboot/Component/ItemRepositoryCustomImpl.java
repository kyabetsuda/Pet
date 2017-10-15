package com.Tsuda.springboot.Component;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.Repository.ItemRepositoryCustom;
import com.Tsuda.springboot.model.Item;
import com.Tsuda.springboot.model.ItemAttribute;

@Repository
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
	private static final long serialVersionUID = 1L;


	EntityManager entityManager;

	public ItemRepositoryCustomImpl(){
		super();
	}

	public ItemRepositoryCustomImpl(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Item> getAllItems(){
		Query query = entityManager.createQuery("from Item");
		List<Item> list = query.getResultList();
		entityManager.close();
		return list;
	}

	@Override
	public List<Item> search(String itemnm, String itemattribute){
		List<Item> results = entityManager
                .createNativeQuery("select * from item where ITEM_NM = :itemnm AND ITEM_ATTRIBUTE = :itemattribute", Item.class)
                .setParameter("itemnm", itemnm)
                .setParameter("itemattribute", itemattribute)
                .getResultList();

        return results;

	}



}