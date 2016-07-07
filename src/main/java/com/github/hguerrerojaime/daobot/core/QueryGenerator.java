package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * @author Humberto Guerrero Jaime
 *
 * @param <T> the Type of the Entity Object
 * @param <K> the Primary Key for the Entity Object
 */
public class QueryGenerator<T extends EntityObject<K>, K extends Serializable> {

	//
    // instance variables
    //

	private EntityManager entityManager;

	private Class<T> entityClass;
	
	private CriteriaBuilder criteriaBuilder;
	
	//
    // constructor
    //

	/**
	 * @param entityManager
	 * @param entityClass
	 */
	public QueryGenerator(EntityManager entityManager, Class<T> entityClass) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	/**
	 * Same as build(true);
	 * @return
	 */
	public ResultSet<T> build() {
		return build(new CB());
	}



	/**
	 * Same as build(new JPACriteriaBuilder(), max, offset, true);
	 * @param max
	 * @param offset
	 * @return
	 */
	public ResultSet<T> build(int max, int offset) {
		return build(new CB(), max, offset);
	}

	
	/**
	 * @param jpaCriteriaBuilder
	 * @return
	 */
	public ResultSet<T> build(AbstractCB criteria) {
		return build(criteria);
	}

	/**
	 * @param jpaCriteriaBuilder
	 * @param max
	 * @param offset
	 * @param autoCount
	 * @return
	 */
	public ResultSet<T> build(AbstractCB criteria,
			int max, int offset) {

		CriteriaQuery<T> criteriaResultQuery = buildSimpleCriteriaQuery(criteria);

		boolean ignoreCount = max <= 0;
		
		Query countQuery = ignoreCount ? null : buildCountQuery(criteria);

		Query resultQuery = entityManager.createQuery(criteriaResultQuery);

		if (max > 0) {
			resultQuery.setMaxResults(max);
		}

		if (offset >= 0) {
			resultQuery.setFirstResult(offset);
		}

		ResultSet<T> queryResult = new ResultSet<T>(resultQuery,
		        countQuery);

		return queryResult;
	}
	
	
	/**
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <R> ResultSet<R> build(List<ExpressionBuilder> expressionBuilders,
			AbstractCB criteria,Class<R> resultClass,int max, int offset) {

		CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(resultClass);
		
		boolean ignoreCount = max <= 0;
		Query countQuery = ignoreCount ? null : buildCountQuery(criteria);

		Root<T> root = criteriaQuery.from(entityClass);
		
		criteriaQuery.multiselect(buildSelection(expressionBuilders, root));

		Predicate filters = buildAndEncapsulateFilters(
				criteria.build(), criteriaBuilder, root);

		criteriaQuery.where(filters);

		List<QuerySort> sortList = criteria.getSortList();

		if (!sortList.isEmpty()) {

			List<javax.persistence.criteria.Order> orderList = buildOrder(sortList, root);

			criteriaQuery.orderBy(orderList);

		}
		
		Query resultQuery = entityManager.createQuery(criteriaQuery);

		return new ResultSet<R>(resultQuery, countQuery);
	}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	private List<Selection<?>> buildSelection(List<ExpressionBuilder> expressionBuilders,Root<T> root) {
		
		List<Selection<?>> selection = new ArrayList<Selection<?>>();
		
		for (ExpressionBuilder eb : expressionBuilders) {
			 Expression expression = eb.build(criteriaBuilder, root);
			 selection.add(expression);
		}
		
		return selection;
		
		
	}
	
	
	public Long getCount(AbstractCB criteria) {
		return (Long) buildCountQuery(criteria).getSingleResult();
	}
	
	public Query buildCountQuery(AbstractCB jpaFilterBuilder){
	    
        CriteriaQuery<Long> criteriaCountQuery = criteriaBuilder
                .createQuery(Long.class);

        Root<T> eoRoot = criteriaCountQuery.from(entityClass);

        criteriaCountQuery.select(criteriaBuilder.count(eoRoot));

        Predicate filters = buildAndEncapsulateFilters(
                jpaFilterBuilder.build(), criteriaBuilder, eoRoot);

        criteriaCountQuery.where(filters);

        return entityManager.createQuery(criteriaCountQuery);

	}

	/**
	 * @param jpaCriteriaBuilder
	 * @return
	 */
	private CriteriaQuery<T> buildSimpleCriteriaQuery(
			AbstractCB jpaCriteriaBuilder) {

		CriteriaQuery<T> criteriaResultQuery = criteriaBuilder
				.createQuery(entityClass);

		Root<T> entityObjectRoot = criteriaResultQuery.from(entityClass);

		criteriaResultQuery.select(entityObjectRoot);

		Predicate filters = buildAndEncapsulateFilters(
				jpaCriteriaBuilder.build(), criteriaBuilder, entityObjectRoot);

		criteriaResultQuery.where(filters);

		List<QuerySort> sortList = jpaCriteriaBuilder.getSortList();

		if (!sortList.isEmpty()) {

			List<javax.persistence.criteria.Order> orderList = buildOrder(sortList, entityObjectRoot);

			criteriaResultQuery.orderBy(orderList);

		}

		return criteriaResultQuery;
	}
	
	
	
	
	/**
	 * @param fg
	 * @param criteriaBuilder
	 * @param eoRoot
	 * @return
	 */
	private Predicate buildAndEncapsulateFilters(FilterGroup fg,
			CriteriaBuilder criteriaBuilder, Root<T> eoRoot) {
		
		QueryFilterGenerator<T, K> queryFilterBuilder =
				new QueryFilterGenerator<T,K>(
						eoRoot,entityClass, criteriaBuilder
				);
		
		return queryFilterBuilder.buildAndEncapsulateFilters(fg);
	
	}
		
	private List<javax.persistence.criteria.Order> buildOrder(List<QuerySort> sortList, Root<T> eoRoot) {

		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();

		for (QuerySort sort : sortList) {

			if (sort.getOrder().equals(Order.ASC)) {

				orderList.add(criteriaBuilder.asc(eoRoot.get(sort.getSort())));

			} else if (sort.getOrder().equals(Order.DESC)) {

				orderList.add(criteriaBuilder.desc(eoRoot.get(sort.getSort())));

			}

		}

		return orderList;

	}

}
