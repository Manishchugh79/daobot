package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.hguerrerojaime.daobot.eo.EntityObject;
import com.github.hguerrerojaime.daobot.utils.Utils;

/**
 * @author Humberto Guerrero Jaime
 *
 * @param <T>
 * @param <K>
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class PredicateGenerator<T extends EntityObject<K>, K extends Serializable> {

	private ConditionFilter conditionFilter;
	private CriteriaBuilder criteriaBuilder;
	private Path<T> eoPath;
	private Class<T> entityClass;

	public PredicateGenerator(ConditionFilter conditionFilter,
			CriteriaBuilder criteriaBuilder, Path<T> eoPath,
			Class<T> entityClass) {
		super();

		this.conditionFilter = conditionFilter;
		this.criteriaBuilder = criteriaBuilder;
		this.eoPath = eoPath;
		this.entityClass = entityClass;
	}

	public Predicate buildIdEq() {

		Field idEqReflectField = Utils.getIdField(entityClass);

		if (idEqReflectField == null) {
			throw new IllegalArgumentException(
					"The entity object does not have a field with the @Id annotation");
		}

		String idEqFieldName = idEqReflectField.getName();
		Path<Serializable> idEqField = eoPath.get(idEqFieldName);

		K idEqValue = (K) conditionFilter.getArgs()[0];

		return criteriaBuilder.equal(idEqField, idEqValue);

	}

	public Predicate buildIdNe() {

		Field idNeReflectField = Utils.getIdField(entityClass);

		if (idNeReflectField == null) {
			throw new IllegalStateException(
					"The entity object does not have a field with the @Id annotation");
		}

		String idNeFieldName = idNeReflectField.getName();
		Path<Serializable> idNeField = eoPath.get(idNeFieldName);

		K idNeValue = (K) conditionFilter.getArgs()[0];

		return criteriaBuilder.notEqual(idNeField, idNeValue);

	}

	public Predicate buildEq() {
		String eqFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression<?> eqField = eoPath.get(eqFieldName);
		Object eqValue = conditionFilter.getArgs()[1];
	
		return criteriaBuilder.equal(eqField, eqValue);
	}

	public Predicate buildEqProperty() {
		String eqPropertyFieldName1 = String
				.valueOf(conditionFilter.getArgs()[0]);
		String eqPropertyFieldName2 = String
				.valueOf(conditionFilter.getArgs()[1]);

		Expression<?> eqPropertyField1 = eoPath
				.get(eqPropertyFieldName1);

		Expression<?> eqPropertyField2 = eoPath
				.get(eqPropertyFieldName2);

		return criteriaBuilder.equal(eqPropertyField1, eqPropertyField2);
	}

	public Predicate buildNe() {
		String neFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression<?> neField = eoPath.get(neFieldName);
		Object neValue = conditionFilter.getArgs()[1];

		return criteriaBuilder.notEqual(neField, neValue);
	}

	public Predicate buildNeProperty() {
		String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
		String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

		Expression<?> field1 = eoPath.get(fieldName1);

		Expression<?> field2 = eoPath.get(fieldName2);

		return criteriaBuilder.notEqual(field1, field2);
	}

	public Predicate buildGt() {
		String gtFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression gtField = eoPath.get(gtFieldName);
		Comparable gtValue = (Comparable) conditionFilter.getArgs()[1];

		return criteriaBuilder.greaterThan(gtField, gtValue);
	}

	public Predicate buildGtProperty() {
		String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
		String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

		Expression field1 = eoPath.get(fieldName1);
		Expression field2 = eoPath.get(fieldName2);

		return criteriaBuilder.greaterThan(field1, field2);
	}

	public Predicate buildGe() {
		String geFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression geField = eoPath.get(geFieldName);
		Comparable geValue = (Comparable) conditionFilter.getArgs()[1];

		return criteriaBuilder.greaterThanOrEqualTo(geField, geValue);
	}

	public Predicate buildGeProperty() {
		String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
		String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

		Expression field1 = eoPath.get(fieldName1);

		Expression field2 = eoPath.get(fieldName2);

		return criteriaBuilder.greaterThanOrEqualTo(field1, field2);
	}

	public Predicate buildLt() {
		String ltFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression ltField = eoPath.get(ltFieldName);
		Comparable ltValue = (Comparable) conditionFilter.getArgs()[1];

		return criteriaBuilder.lessThan(ltField, ltValue);
	}

	public Predicate buildLtProperty() {
		String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
		String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

		Expression field1 = eoPath.get(fieldName1);

		Expression field2 = eoPath.get(fieldName2);

		return criteriaBuilder.lessThan(field1, field2);
	}

	public Predicate buildLe() {
		String leFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression leField = eoPath.get(leFieldName);
		Comparable leValue = (Comparable) conditionFilter.getArgs()[1];

		return criteriaBuilder.lessThanOrEqualTo(leField, leValue);
	}

	public Predicate buildLeProperty() {
		String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
		String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

		Expression field1 = eoPath.get(fieldName1);

		Expression field2 = eoPath.get(fieldName2);

		return criteriaBuilder.lessThanOrEqualTo(field1, field2);
	}

	public Predicate buildLike() {
		String likeFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression<String> likeField = eoPath.get(likeFieldName);
		String likeValue = (String) conditionFilter.getArgs()[1];

		return criteriaBuilder.like(likeField, likeValue);
	}

	public Predicate buildILike() {
		String ilikeFieldName = String.valueOf(conditionFilter.getArgs()[0]);

		Expression<String> ilikeField = eoPath.get(ilikeFieldName);

		String ilikeValue = (String) conditionFilter.getArgs()[1];

		return criteriaBuilder.like(criteriaBuilder.upper(ilikeField),
				ilikeValue.toUpperCase());
	}

	public Predicate buildIsNull() {
		String isNullFieldName = String.valueOf(conditionFilter.getArgs()[0]);
		Expression<? extends Comparable<?>> isNullField = eoPath
				.get(isNullFieldName);

		return criteriaBuilder.isNull(isNullField);
	}

	public Predicate buildIsNotNull() {
		String isNotNullFieldName = String
				.valueOf(conditionFilter.getArgs()[0]);
		Expression<? extends Comparable<?>> isNotNullField = eoPath
				.get(isNotNullFieldName);

		return criteriaBuilder.isNotNull(isNotNullField);
	}

	public Predicate buildIn() {
		String inFieldName = String.valueOf(conditionFilter.getArgs()[0]);
		Expression<? extends Comparable<?>> inField = eoPath.get(inFieldName);

		Collection<?> inValue = (Collection<?>) conditionFilter.getArgs()[1];

		return inField.in(inValue);
	}

	public Predicate buildBetween() {
		Expression betweenField = eoPath.get(String
				.valueOf(conditionFilter.getArgs()[0]));

		Object betweenLowValArg = conditionFilter.getArgs()[1];
		Object betweenHighValArg = conditionFilter.getArgs()[2];

		Comparable betweenLowVal = (Comparable) betweenLowValArg;
		Comparable betweenHighVal = (Comparable) betweenHighValArg;

		return criteriaBuilder.between(betweenField, betweenLowVal,
				betweenHighVal);
	}

	public Predicate buildJoin(){
		
		
		String joinFieldName = String.valueOf(conditionFilter.getArgs()[0]);
		AbstracQueryBuilder filterBuilder = (AbstracQueryBuilder) conditionFilter.getArgs()[1];
		JoinType joinType = (JoinType) conditionFilter.getArgs()[2];
		
		Root<T> eoRoot = (Root<T>) eoPath;
		
		Path joinRoot = eoRoot.join(joinFieldName,joinType);
		
		Field joinField = null;
		
		try {
			joinField = entityClass.getDeclaredField(joinFieldName);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("Join field not found",e);
		} catch (SecurityException e) {
			throw new IllegalArgumentException("Join field not accessible",e);
		}
		
		
		QueryFilterGenerator queryFilterBuilder = new QueryFilterGenerator(
				joinRoot, (Class<?>) joinField.getType(), criteriaBuilder);
		
		return queryFilterBuilder.buildAndEncapsulateFilters(filterBuilder.build());
		
	}
}
