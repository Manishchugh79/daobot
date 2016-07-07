package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

@SuppressWarnings("rawtypes")
public class DynamicQueryBuilder<T extends EntityObject<K>,K extends Serializable> {
	
	private static final int SINGLE_RESULT = 1;

	private QueryGenerator<T, K> queryGenerator;
	private List<ExpressionBuilder> selection;
	private AbstractCB criteria;
	
	public DynamicQueryBuilder(EntityManager em,Class<T> entityClass) {
		queryGenerator = new QueryGenerator<T, K>(em, entityClass);
		selection = new ArrayList<ExpressionBuilder>();
		criteria = new CB();
	}
	
	
	public DynamicQueryBuilder<T,K> select(ExpressionBuilder... selectExpressions) {
		selection.addAll(Arrays.asList(selectExpressions));
		return this;
	}
	
	
	public DynamicQueryBuilder<T,K> withCriteria(AbstractCB criteria) {
		this.criteria = criteria;
		return this;
	}
	
	public <R> R find(Class<R> resultClass) {
		return (R) queryGenerator.build(selection, criteria,resultClass,SINGLE_RESULT,0).get();
	}
	
	public <R> ResultSet<R> findAll(Class<R> resultClass) {
		return findAll(resultClass,0,0);
	}
	
	public <R> ResultSet<R> findAll(Class<R> resultClass,int max,int offset) {
		ResultSet<R> rs = queryGenerator.build(selection, criteria,resultClass,max,offset);
		return rs;
	}

	public List<ExpressionBuilder> getSelection() {
		return selection;
	}
	

}
