package com.github.hguerrerojaime.daobot.core;

/**
 * Objects that encapsulate the sorting for the JPACriteriaBuilder
 * 
 * @author Humberto Guerrero Jaime
 *
 */
public class QuerySort {
	
	//
    // instance variables
    //
	
	private String sort;
	private DOrder order;
	
	//
    // constructor
    //
	
	public QuerySort(String sort, DOrder order) {
		super();
		this.sort = sort;
		this.order = order;
	}


	//
    // getters and setters
    //


	/**
	 * @return the sort field
	 */
	public String getSort() {
		return sort;
	}




	/**
	 * @param sort - The field to be sorted
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}




	/**
	 * @return the order that the field is going to be sorted
	 */
	public DOrder getOrder() {
		return order;
	}




	/**
	 * @param order - the order that the field is going to be sorted
	 */
	public void setOrder(DOrder order) {
		this.order = order;
	}


}
