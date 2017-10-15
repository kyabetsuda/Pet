package com.Tsuda.springboot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="item_attribute")
public class ItemAttribute {


	@Id
	@Column(name="ITEM_ATTRIBUTE_NO")
	private int itemattributeno;

	@Column(name="ITEM_ATTRIBUTE_NM")
	private String itemattributenm;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ITEM_ATTRIBUTE", referencedColumnName = "ITEM_ATTRIBUTE_NO" )
	private List<Item> items = null;

	public void setItemattributeno(int itemattributeno){
		this.itemattributeno = itemattributeno;
	}

	public int getItemattributeno(){
		return itemattributeno;
	}

	public void setItemattributenm(String itemattributenm){
		this.itemattributenm = itemattributenm;
	}

	public String getItemattributenm(){
		return itemattributenm;
	}

	public void addItem(Item item) {
        if (items == null ){
            items = new ArrayList<>();
        }

        if (!items.contains(item))  {
            items.add(item);
        }
    }

	public List<Item> getItems(){
		return items;
	}

}
