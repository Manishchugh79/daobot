package com.hgj.daobot.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Humberto Guerrero Jaime
 *
 */
public class FilterGroup implements QueryFilter {

	private List<QueryFilter> filters;
	private Type groupType;
	
	public FilterGroup(){
		this(Type.AND);
	}
	
	protected FilterGroup(Type groupType){
		this.groupType = groupType;
	}
	
	public Type getGroupType() {
		return groupType;
	}
	
	protected void setGroupType(Type groupType){
		this.groupType = groupType;
	}

	public List<QueryFilter> getFilters() {
		
		if(filters == null){
			filters = new ArrayList<QueryFilter>();
		}
		return filters;
	}
	

	public static enum Type{
		AND,OR,NAND,NOR
	}
	
}
