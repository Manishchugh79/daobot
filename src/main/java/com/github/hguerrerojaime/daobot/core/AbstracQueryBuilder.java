package com.github.hguerrerojaime.daobot.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstracQueryBuilder {
	
	private List<QueryFilter> filters;
	private FilterGroup.Type filterGroupType;

	/**
	 * Constructor with a default encapsulating AND filterGroupType
	 * 
	 * @param filterGroupType
	 */
	public AbstracQueryBuilder() {
		this(FilterGroup.Type.AND);
	}
	
	/**
	 * Constructor indicating the encapsulating filterGroupType
	 * 
	 * @param filterGroupType
	 */
	public AbstracQueryBuilder(FilterGroup.Type filterGroupType){
		this.filterGroupType = filterGroupType;
		filters = new ArrayList<QueryFilter>();
		
	}
	
	protected List<QueryFilter> getFilters() {
		return filters;
	}

	protected FilterGroup.Type getFilterGroupType() {
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
