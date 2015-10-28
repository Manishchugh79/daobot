package com.github.hguerrerojaime.daobot.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Humberto Guerrero Jaime
 *
 */
public class CB extends FB{
	
	private List<QuerySort> sortList;
	
	public CB() {
		this(FilterGroup.Type.AND);
	}
	
	public CB(FilterGroup.Type filterGroupType){
		super(filterGroupType);
		sortList = new ArrayList<QuerySort>();
	}
	
	public CB sort(String fieldName){
		return sort(fieldName,DOrder.ASC);
	}
	
	public CB sort(String fieldName,DOrder order){
		QuerySort querySort = new QuerySort(fieldName, order);
		getSortList().add(querySort);
		return this;
	}

	public List<QuerySort> getSortList() {
		return sortList;
	}
	
	
	
	
}
