package com.github.hguerrerojaime.daobot.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstracQueryBuilder {
	
	private List<QueryFilter> filters;
	private FilterGroupType filterGroupType;

	/**
	 * Constructor with a default encapsulating AND filterGroupType
	 * 
	 * @param filterGroupType
	 */
	public AbstracQueryBuilder() {
		this(FilterGroupType.AND);
	}
	
	/**
	 * Constructor indicating the encapsulating filterGroupType
	 * 
	 * @param filterGroupType
	 */
	public AbstracQueryBuilder(FilterGroupType filterGroupType){
		this.filterGroupType = filterGroupType;
		filters = new ArrayList<QueryFilter>();
		
	}
	
	protected List<QueryFilter> getFilters() {
		return filters;
	}

	protected FilterGroupType getFilterGroupType() {
		return filterGroupType;
	}
	
	/**
	 * @param queryFilter
	 * @return
	 */
	protected QueryFilter addQueryFilter(QueryFilter queryFilter){
		getFilters().add(queryFilter);
		return queryFilter;
	}

	public abstract FilterGroup build();
	
	

}
