package com.github.hguerrerojaime.daobot.core;

import java.util.List;
import javax.persistence.Query;

/**
 * Encapsulates the result query and an optional count query
 * 
 * @author Humberto Guerrero Jaime
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class ResultSet<T> {
	
	private static final int SINGLE_RESULT = 1;
	
	//
    // instance variables
    //

	private Query resultQuery;
	private Query countQuery;
	private Long totalCount;
	
	private List<T> list;
	
	
	
	//
    // constructor
    //

	
	/**
	 * @param result The result Query
	 * @param totalCount the total count of the results found
	 */
	public ResultSet(Query resultQuery, Query countQuery) {
		super();
		this.resultQuery = resultQuery;
		this.countQuery = countQuery;
	}
	
	
	/**
	 * @return the Result List of the query
	 */
	public List<T> list() {
		
		if (list == null) {
			list = resultQuery.getResultList();
		}
		
		return list;
	}
	
	/**
	 * @return the first Result of the query
	 */
	public T get() {
		
		int maxResultsTmp = resultQuery.getMaxResults();
		
		resultQuery.setMaxResults(SINGLE_RESULT);
		
		int resultListSize = list().size();
		
		if(resultListSize == 0){
			return null;
		}else{
			T result = list().get(0);
			
			resultQuery.setMaxResults(maxResultsTmp);
			
			return result;
		}
	
	}
	
	//
    // getters and setters
    //		
	
	/**
	 * @return The total count of the records found with the criteria
	 */
	public Long count() {
		
		if (countQuery == null) {
			totalCount = (long) list().size();
		} else if(totalCount == null){
	        totalCount = (Long) countQuery.getSingleResult();
	    }
	    
		return totalCount;
	}	

}
