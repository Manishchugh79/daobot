package com.github.hguerrerojaime.daobot.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Humberto Guerrero Jaime
 *
 */
public class FilterGroup implements QueryFilter {

	private List<QueryFilter> filters;
	private FilterGroupType groupType;
	
	protected FilterGroup(FilterGroupType groupType){
		this.groupType = groupType;
	}
	
	public FilterGroupType getGroupType() {
		return groupType;
	}
	
	protected void setGroupType(FilterGroupType groupType){
		this.groupType = groupType;
	}

	public List<QueryFilter> getFilters() {
		
		if(filters == null){
			filters = new ArrayList<QueryFilter>();
		}
		return filters;
	}
	

}
