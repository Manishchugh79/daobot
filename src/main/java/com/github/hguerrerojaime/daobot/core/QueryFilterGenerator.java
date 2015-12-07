package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.github.hguerrerojaime.daobot.eo.EntityObject;

public class QueryFilterGenerator<T extends EntityObject<K>,K extends Serializable> {
	
	
	private Class<T> entityClass;
	private CriteriaBuilder criteriaBuilder;
	private Path<T> eoPath;
	
	
	
	public QueryFilterGenerator(Path<T> eoPath,Class<T> entityClass,
			CriteriaBuilder criteriaBuilder) {
		super();
		this.eoPath = eoPath;
		this.entityClass = entityClass;
		this.criteriaBuilder = criteriaBuilder;
	}

	/**
	 * @param fg
	 * @param criteriaBuilder
	 * @param eoPath
	 * @return
	 */
	public Predicate buildAndEncapsulateFilters(FilterGroup fg) {
		
		List<Predicate> criteriaList = buildFilters(fg, criteriaBuilder);

		return encapsulateFilters(fg, criteriaBuilder, criteriaList);

	}
	
	/**
	 * @param fg
	 * @param criteriaBuilder
	 * @param eoPath
	 * @return
	 */
	private List<Predicate> buildFilters(FilterGroup fg,
			CriteriaBuilder criteriaBuilder) {

		List<Predicate> criteriaList = new ArrayList<Predicate>();

		for (QueryFilter queryFilter : fg.getFilters()) {

			if (queryFilter instanceof FilterGroup) {
				FilterGroup filterGroup = (FilterGroup) queryFilter;
				criteriaList.add(buildAndEncapsulateFilters(filterGroup));
			} else if (queryFilter instanceof ConditionFilter) {
				ConditionFilter conditionFilter = (ConditionFilter) queryFilter;
				criteriaList.add(buildConditionFilter(conditionFilter));
			}

		}

		return criteriaList;
	}

	/**
	 * @param fg
	 * @param criteriaBuilder
	 * @param criteriaList
	 * @return
	 */
	private Predicate encapsulateFilters(FilterGroup fg,
			CriteriaBuilder criteriaBuilder, List<Predicate> criteriaList) {

		return fg.getGroupType().getBuilder().build(criteriaBuilder, criteriaList);
	}

	


	/**
	 * @param conditionFilter
	 * @param criteriaBuilder
	 * @param eoPath
	 * @return
	 */
	private Predicate buildConditionFilter(ConditionFilter conditionFilter) {
		return conditionFilter.getConditionType().getBuilder().build(
		        conditionFilter, criteriaBuilder, eoPath, entityClass);

	}

	/**
	 * @return the entityClass
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * @return the criteriaBuilder
	 */
	public CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}

	
	
}
