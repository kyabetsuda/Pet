package com.Tsuda.springboot.Component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
		 CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		 CriteriaQuery<Item> criteria_query =
		            builder.createQuery(Item.class);
		 Root<Item> root = criteria_query.from(Item.class);
		 criteria_query.select(root);
		 List<Predicate> preds = new ArrayList<>();
		 if( itemnm == "" ) {
				if( itemattribute.equals("all") ) {
					
				}else {
					preds.add(builder.equal(root.get("itemattribute"), itemattribute));
				}
		 }else {
				if( itemattribute.equals("all") ) {
					preds.add(builder.equal(root.get("itemnm"), itemnm));
				}else {
					preds.add(builder.equal(root.get("itemnm"), itemnm));
					preds.add(builder.equal(root.get("itemattribute"), itemattribute));
				}
		 }
		 criteria_query.where(builder.and(preds.toArray(new Predicate[]{})));
		 Query query = entityManager.createQuery(criteria_query);
		 List<Item> list = query.getResultList();

		 return list;
        
	}



}