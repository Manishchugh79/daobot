package com.github.hguerrerojaime.daobot.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The Abstract query builder, can have many implementations
 * 
 * @author Humberto Guerrero Jaime
 *
 */
public abstract class AbstractCB {
	
	private List<QueryFilter> filters;
	private List<QuerySort> sortList;
	private FilterGroupType filterGroupType;
	

	/**
	 * Constructor
	 * 
	 * @param filterGroupType
	 */
	public AbstractCB(FilterGroupType filterGroupType){
		this.filterGroupType = filterGroupType;
		filters = new ArrayList<QueryFilter>();
		sortList = new ArrayList<QuerySort>();
	}
	
	/**
	 * Gets the existing filters
	 * 
	 * @return the existing filters
	 */
	protected List<QueryFilter> getFilters() {
		return filters;
	}



	/**
	 * Gets the filter group type
	 * 
	 * @return the filter group type
	 */
	protected FilterGroupType getFilterGroupType() {
		return filterGroupType;
	}
	
	/**
	 * 
	 * Adds a query filter to the filter list
	 * 
	 * @param queryFilter
	 * @return
	 */
	protected QueryFilter addQueryFilter(QueryFilter queryFilter){
		getFilters().add(queryFilter);
		return queryFilter;
	}

	/**
	 * The abstract Build Method
	 * 
	 * @return
	 */
	public abstract FilterGroup build();


	/**
	 * @return the sort list class
	 */
	protected List<QuerySort> getSortList() {
		return sortList;
	}
}
