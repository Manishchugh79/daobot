package com.hgj.daobot.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.hgj.daobot.eo.EntityObject;

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

		switch (fg.getGroupType()) {

		case AND:
			return criteriaBuilder.and(criteriaList.toArray(new Predicate[0]));
		case OR:
			return criteriaBuilder.or(criteriaList.toArray(new Predicate[0]));
		case NAND:
			return criteriaBuilder.not(criteriaBuilder.and(criteriaList
					.toArray(new Predicate[0])));
		case NOR:
			return criteriaBuilder.not(criteriaBuilder.or(criteriaList
					.toArray(new Predicate[0])));
		default:
			return null;
		}
	}

	


	/**
	 * @param conditionFilter
	 * @param criteriaBuilder
	 * @param eoPath
	 * @return
	 */
	private Predicate buildConditionFilter(ConditionFilter conditionFilter) {

		PredicateGenerator<T, K> predicateBuilder = new PredicateGenerator<T, K>(
				conditionFilter, criteriaBuilder, eoPath, entityClass);

		switch (conditionFilter.getConditionType()) {
		case ID_EQ:
			return predicateBuilder.buildIdEq();
		case ID_NE:
			return predicateBuilder.buildIdNe();
		case EQ:
			return predicateBuilder.buildEq();
		case EQ_PROPERTY:
			return predicateBuilder.buildEqProperty();
		case NE:
			return predicateBuilder.buildNe();
		case NE_PROPERTY:
			return predicateBuilder.buildNeProperty();
		case GT:
			return predicateBuilder.buildGt();
		case GT_PROPERTY:
			return predicateBuilder.buildGtProperty();
		case GE:
			return predicateBuilder.buildGe();
		case GE_PROPERTY:
			return predicateBuilder.buildGeProperty();
		case LT:
			return predicateBuilder.buildLt();
		case LT_PROPERTY:
			return predicateBuilder.buildLtProperty();
		case LE:
			return predicateBuilder.buildLe();
		case LE_PROPERTY:
			return predicateBuilder.buildLeProperty();
		case LIKE:
			return predicateBuilder.buildLike();
		case ILIKE:
			return predicateBuilder.buildILike();
		case IS_NULL:
			return predicateBuilder.buildIsNull();
		case IS_NOT_NULL:
			return predicateBuilder.buildIsNotNull();
		case IN:
			return predicateBuilder.buildIn();
		case BETWEEN:
			return predicateBuilder.buildBetween();
		case JOIN:
			return predicateBuilder.buildJoin();
		default:
			break;
		}

		return null;

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
