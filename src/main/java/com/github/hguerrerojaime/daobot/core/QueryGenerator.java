package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	}

	/**
	 * Same as build(true);
	 * @return
	 */
	public ResultSet<T> build() {
		return build(true);
	}

	/**
	 * Same as build(new JPACriteriaBuilder(), autoCount);
	 * @param autoCount
	 * @return
	 */
	public ResultSet<T> build(boolean autoCount) {
		return build(new CB(), autoCount);
	}

	/**
	 * Same as build(new JPACriteriaBuilder(), max, offset, true);
	 * @param max
	 * @param offset
	 * @return
	 */
	public ResultSet<T> build(int max, int offset) {
		return build(new CB(), max, offset, true);
	}

	/**
	 * 
	 * @param max
	 * @param offset
	 * @param autoCount
	 * @return
	 */
	public ResultSet<T> build(int max, int offset, boolean autoCount) {
		return build(new CB(), max, offset, autoCount);
	}

	/**
	 * @param jpaCriteriaBuilder
	 * @param max
	 * @param offset
	 * @return
	 */
	public ResultSet<T> build(AbstractCB jpaCriteriaBuilder,
			int max, int offset) {
		return build(jpaCriteriaBuilder, max, offset, true);
	}

	/**
	 * @param jpaCriteriaBuilder
	 * @return
	 */
	public ResultSet<T> build(AbstractCB jpaCriteriaBuilder) {
		return build(jpaCriteriaBuilder, true);
	}

	/**
	 * @param jpaCriteriaBuilder
	 * @param autoCount
	 * @return
	 */
	public ResultSet<T> build(AbstractCB jpaCriteriaBuilder,
			boolean autoCount) {
		return build(jpaCriteriaBuilder, 0, 0, autoCount);
	}

	/**
	 * @param jpaCriteriaBuilder
	 * @param max
	 * @param offset
	 * @param autoCount
	 * @return
	 */
	public ResultSet<T> build(AbstractCB jpaCriteriaBuilder,
			int max, int offset, boolean autoCount) {

		CriteriaQuery<T> criteriaResultQuery = buildCriteriaResultQuery(jpaCriteriaBuilder);

		Query countQuery = null;

		if (autoCount) {
		    countQuery = buildCountQuery(jpaCriteriaBuilder);
		}

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
     * @param jpaCriteriaBuilder
     * @param max
     * @param offset
     * @param autoCount
     * @return
     */
    public ResultSet<T> build(JsonCB jpaCriteriaBuilder,
            int max, int offset, boolean autoCount) {

        CriteriaQuery<T> criteriaResultQuery = buildCriteriaResultQuery(jpaCriteriaBuilder);

        Query countQuery = null;

        if (autoCount) {
            countQuery = buildCountQuery(jpaCriteriaBuilder);
        }

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

	public Long getCount(AbstractCB jpaFilterBuilder) {
		return (Long) buildCountQuery(jpaFilterBuilder).getSingleResult();
	}
	
	public Query buildCountQuery(AbstractCB jpaFilterBuilder){
	    
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

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
	private CriteriaQuery<T> buildCriteriaResultQuery(
			AbstractCB jpaCriteriaBuilder) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> criteriaResultQuery = criteriaBuilder
				.createQuery(entityClass);

		Root<T> entityObjectRoot = criteriaResultQuery.from(entityClass);

		criteriaResultQuery.select(entityObjectRoot);

		Predicate filters = buildAndEncapsulateFilters(
				jpaCriteriaBuilder.build(), criteriaBuilder, entityObjectRoot);

		criteriaResultQuery.where(filters);

		List<QuerySort> sortList = jpaCriteriaBuilder.getSortList();

		if (!sortList.isEmpty()) {

			List<javax.persistence.criteria.Order> orderList = buildOrder(sortList, entityObjectRoot,
					criteriaBuilder);

			criteriaResultQuery.orderBy(orderList);

		}

		return criteriaResultQuery;
	}
	
	/**
     * @param jpaCriteriaBuilder
     * @return
     */
    private CriteriaQuery<T> buildCriteriaResultQuery(
            JsonCB jpaCriteriaBuilder) {
        
        FilterGroup filterGroup = jpaCriteriaBuilder.build();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> criteriaResultQuery = criteriaBuilder
                .createQuery(entityClass);

        Root<T> entityObjectRoot = criteriaResultQuery.from(entityClass);

        criteriaResultQuery.select(entityObjectRoot);

        Predicate filters = buildAndEncapsulateFilters(
                filterGroup, criteriaBuilder, entityObjectRoot);

        criteriaResultQuery.where(filters);

        List<QuerySort> sortList = jpaCriteriaBuilder.getSortList();

        if (!sortList.isEmpty()) {

            List<javax.persistence.criteria.Order> orderList = buildOrder(sortList, entityObjectRoot,
                    criteriaBuilder);

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
		
	private List<javax.persistence.criteria.Order> buildOrder(List<QuerySort> sortList, Root<T> eoRoot,
			CriteriaBuilder criteriaBuilder) {

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
