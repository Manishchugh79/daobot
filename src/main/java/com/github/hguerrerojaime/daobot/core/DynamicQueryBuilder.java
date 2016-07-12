package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.expression.RootExpressionBuilder;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * DynamicQueryBuilder
 * 
 * to perform dynamic selection with filters
 * 
 * @author Humberto Guerrero Jaime
 *
 * @param <T>
 * @param <K>
 */
@SuppressWarnings("rawtypes")
public class DynamicQueryBuilder<T extends EntityObject<K>,K extends Serializable> {
	
	private static final int SINGLE_RESULT = 1;

	private ResultGenerator<T, K> queryGenerator;
	private List<ExpressionBuilder> selection;
	private AbstractCB criteria;
	
	public DynamicQueryBuilder(EntityManager em,Class<T> entityClass) {
		queryGenerator = new ResultGenerator<T, K>(em, entityClass);
		selection = new ArrayList<ExpressionBuilder>();
		criteria = new CB();
	}
	
	
	/**
	 * Selects the expressions to be returned
	 * @param selectExpressions the expressions
	 * @return
	 */
	public DynamicQueryBuilder<T,K> select(ExpressionBuilder... selectExpressions) {
		selection.addAll(Arrays.asList(selectExpressions));
		return this;
	}
	
	
	/**
	 * Constains the Filters and Sorts
	 * 
	 * @param criteria - the criteria object
	 * @return
	 */
	public DynamicQueryBuilder<T,K> withCriteria(AbstractCB criteria) {
		this.criteria = criteria;
		return this;
	}
	
	/**
	 * Fetches the first result
	 * @param resultClass - the expected result type
	 * @return the result
	 */
	public <R> R find(Class<R> resultClass) {
		return (R) queryGenerator.build(getSelection(), criteria,resultClass,SINGLE_RESULT,0).get();
	}
	
	/**
	 * Fetches the result list
	 * 
	 * @return the result list
	 */
	public ResultSet<?> findAll() {
		return findAll(Object.class,0,0);
	}
	
	/**
	 * Fetches the result list with an expecting result object
	 * 
	 * @param resultClass the expected record type
	 * @return the result list
	 */
	public <R> ResultSet<R> findAll(Class<R> resultClass) {
		return findAll(resultClass,0,0);
	}
	
	/**
	 * Fetches the paginated result list with an expecting result object
	 * 
	 * @param resultClass the expected record type
	 * @return the result list
	 */
	public <R> ResultSet<R> findAll(Class<R> resultClass,int max,int offset) {
		ResultSet<R> rs = queryGenerator.build(getSelection(), criteria,resultClass,max,offset);
		return rs;
	}

	/**
	 * @return the current selection
	 */
	public List<ExpressionBuilder> getSelection() {
		
		if (selection.isEmpty()) {
			selection.add(new RootExpressionBuilder<T>());
		}
		
		return selection;
	}
	

}
