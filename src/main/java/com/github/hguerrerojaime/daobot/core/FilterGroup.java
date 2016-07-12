package com.github.hguerrerojaime.daobot.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for Encapsulating Filter Types
 * 
 * @author Humberto Guerrero Jaime
 *
 */
public class FilterGroup implements QueryFilter {

	private List<QueryFilter> filters;
	private FilterGroupType groupType;
	
	protected FilterGroup(FilterGroupType groupType){
		this.groupType = groupType;
	}
	
	/**
	 * @return the Group Type (AND,OR,NOT)
	 */
	public FilterGroupType getGroupType() {
		return groupType;
	}
	
	/**
	 * Sets the group type
	 * 
	 * @param groupType
	 */
	protected void setGroupType(FilterGroupType groupType){
		this.groupType = groupType;
	}

	/**
	 * 
	 * @return The filters being encapsulated by this filer group
	 */
	public List<QueryFilter> getFilters() {
		
		if(filters == null){
			filters = new ArrayList<QueryFilter>();
		}
		return filters;
	}
	

}
