package com.github.hguerrerojaime.daobot.core;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;

import javax.persistence.criteria.JoinType;

import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.path;
import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.upper;

import java.util.Collection;

/**
 * @author Humberto Guerrero Jaime
 *
 */
@SuppressWarnings("rawtypes")
public class CB extends AbstractCB {
	


	private FilterGroup filterBuild;

	/**
	 * Constructor with a default encapsulating AND filterGroupType
	 *
	 */
	public CB() {
		super(FilterGroupType.AND);
	}


	/**
	 * Builds and returns a FilterGroup
	 *
	 * @return The built FilterGroup
	 */
	public FilterGroup build(){
		filterBuild = new FilterGroup(getFilterGroupType());
		filterBuild.getFilters().addAll(getFilters());
		return filterBuild;
	}

	/**
	 * @return The built FilterGroup, has to be build before
	 */
	public FilterGroup getBuild(){
		return filterBuild;
	}


	/**
	 * Filter that sets an AND to the result of a given group of conditions
	 * @param filterBuilder
	 * @return
	 */
	public CB and(CB filterBuilder){

		FilterGroup filterGroup = filterBuilder.build();
		filterGroup.setGroupType(FilterGroupType.AND);

		addQueryFilter(filterGroup);

		return this;
	}

	/**
	 * Filter that sets an OR to the result of a given group of conditions
	 * @param filterBuilder
	 * @return
	 */
	public CB or(CB filterBuilder){

		FilterGroup filterGroup = filterBuilder.build();
		filterGroup.setGroupType(FilterGroupType.OR);
		addQueryFilter(filterGroup);
		return this;
	}

	/**
	 * Filter that sets a NOT to the result of a given group of conditions
	 * @param filterBuilder
	 * @return
	 */
	public CB not(CB filterBuilder){
		FilterGroup filterGroup = filterBuilder.build();
		filterGroup.setGroupType(FilterGroupType.NAND);
		addQueryFilter(filterGroup);

		return this;
	}

	public CB eq(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.EQ, eb,value));
		return this;
	}

	
	public CB eq(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.EQ, eb1,eb2));
		return this;
	}

	public CB eq(String fieldName,Object value){
		return eq(path(fieldName),value);
	}

	public CB eqProperty(String fieldName1,String fieldName2){
		return eq(path(fieldName1),path(fieldName2));
	}

	public CB ne(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.NE, eb,value));
		return this;
	}

	public CB ne(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.NE, eb1,eb2));
		return this;
	}

	public CB ne(String fieldName,Comparable<?> value){
		return ne(path(fieldName),value);
	}


	public CB neProperty(String fieldName1,String fieldName2){
		return ne(path(fieldName1),path(fieldName2));
	}

	public CB gt(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GT, eb,value));
		return this;
	}
	public CB gt(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GT, eb1,eb2));
		return this;
	}

	public CB gt(String fieldName,Comparable value){
		return gt(path(fieldName),value);
	}

	public CB gtProperty(String fieldName1,String fieldName2){
		return gt(path(fieldName1),path(fieldName2));
	}

	public CB ge(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GE, eb,value));
		return this;
	}

	public CB ge(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GE, eb1,eb2));
		return this;
	}

	public CB ge(String fieldName,Comparable value){
		return ge(path(fieldName),value);
	}

	public CB geProperty(String fieldName1,String fieldName2){
		return ge(path(fieldName1),path(fieldName2));
	}


	public CB lt(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LT, eb,value));
		return this;
	}

	public CB lt(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LT, eb1,eb2));
		return this;
	}

	public CB lt(String fieldName,Comparable value){
		return lt(path(fieldName),value);
	}

	public CB ltProperty(String fieldName1,String fieldName2){
		return lt(path(fieldName1),path(fieldName2));
	}

	public CB le(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LE, eb,value));
		return this;
	}

	public CB le(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LE, eb1,eb2));
		return this;
	}

	public CB le(String fieldName,Comparable value){
		return le(path(fieldName),value);
	}

	public CB leProperty(String fieldName1,String fieldName2){
		return le(path(fieldName1),path(fieldName2));
	}

	public CB in(ExpressionBuilder eb,Collection<?> values){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IN, eb,values));
		return this;
	}

	public CB in(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IN, eb1,eb2));
		return this;
	}

	public CB in(String fieldName,Collection<?> values){
		return in(path(fieldName),values);
	}

	public CB like(ExpressionBuilder eb,String value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LIKE, eb,value));
		return this;
	}

	public CB like(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LIKE, eb1,eb2));
		return this;
	}

	public CB like(String fieldName,String value){
		return like(path(fieldName),value);
	}

	public CB likeProperty(String fieldName1,String fieldName2){
		return like(path(fieldName1),path(fieldName2));
	}

	public CB ilike(ExpressionBuilder eb,String value){
		return like(upper(eb),value.toUpperCase());
	}

	public CB ilike(ExpressionBuilder eb1,ExpressionBuilder eb2){
		return like(upper(eb1),upper(eb2));
	}

	public CB ilike(String fieldName,String value){
		return ilike(path(fieldName),value);
	}

	public CB ilikeProperty(String fieldName1,String fieldName2){
		return ilike(path(fieldName1),path(fieldName2));
	}

	public CB isNull(ExpressionBuilder eb){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IS_NULL, eb));
		return this;
	}

	public CB isNull(String fieldName){
		return isNull(path(fieldName));
	}

	public CB isNotNull(ExpressionBuilder eb){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IS_NOT_NULL, eb));
		return this;
	}

	public CB isNotNull(String fieldName){
		return isNotNull(path(fieldName));
	}

	public CB between(ExpressionBuilder eb,Comparable lowValue,Comparable highValue){
		addQueryFilter(new ConditionFilter(ConditionFilterType.BETWEEN, eb,lowValue,highValue));
		return this;
	}

	public CB between(ExpressionBuilder eb1, ExpressionBuilder eb2, ExpressionBuilder eb3){
		addQueryFilter(new ConditionFilter(ConditionFilterType.BETWEEN, eb1,eb2,eb3));
		return this;
	}

	public CB between(String fieldName,Comparable lowValue,Comparable highValue){
		return between(path(fieldName),lowValue,highValue);
	}

	/**
	 * Filter that joins conditions from a related Entity Object
	 * same as join(fieldName,JoinType.INNER,filterBuilder);
	 * @param fieldName the entity related property
	 * @param filterBuilder the related filter builder
	 * @return the generated condition filter
	 */
	public CB join(String fieldName,CB filterBuilder){
		return join(fieldName, JoinType.INNER,filterBuilder);
	}


	/**
	 * @param fieldName
	 * @param joinType
	 * @param filterBuilder
	 * @return
	 */
	public CB join(String fieldName,JoinType joinType,CB filterBuilder){
		addQueryFilter(new ConditionFilter(ConditionFilterType.JOIN, new Object[]{ fieldName,filterBuilder,joinType }));
		return this;
	}

	
	public CB sort(String fieldName){
		return sort(fieldName, Order.ASC);
	}
	
	public CB sort(String fieldName,Order order){
		QuerySort querySort = new QuerySort(fieldName, order);
		getSortList().add(querySort);
		return this;
	}

}
