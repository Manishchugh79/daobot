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
	private Order order;
	
	//
    // constructor
    //
	
	public QuerySort(String sort, Order order) {
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
	 * @return the order that the field is going to be sorted
	 */
	public Order getOrder() {
		return order;
	}


}
