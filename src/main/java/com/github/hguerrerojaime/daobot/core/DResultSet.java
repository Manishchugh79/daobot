package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * @author Humberto Guerrero Jaime
 *
 * @param <T> Entity Object Type
 */
@SuppressWarnings("unchecked")
public class DResultSet<T extends EntityObject<? extends Serializable>> {
	
	//
    // instance variables
    //

	private Query result;
	private Long totalCount;
	
	
	//
    // constructor
    //

	
	/**
	 * @param result The result Query
	 * @param totalCount the total count of the results found
	 */
	public DResultSet(Query result, Long totalCount) {
		super();
		this.result = result;
		this.totalCount = totalCount;
	}
	
	
	/**
	 * @return the Result List of the query
	 */
	public List<T> list() {
		return result.getResultList();
	}
	
	/**
	 * @return the first Result of the query
	 */
	public T get() {
		
		int resultListSize = result.getResultList().size();
		
		if(resultListSize == 0){
			return null;
		}else{
			return (T) result.getResultList().get(0);
		}
	
	}
	
	//
    // getters and setters
    //		
	
	/**
	 * @return The total count of the records found with the criteria
	 */
	public Long count() {
		return totalCount;
	}

	

}
