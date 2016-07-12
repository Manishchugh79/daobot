package com.github.hguerrerojaime.daobot.core;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;

import javax.persistence.criteria.JoinType;

import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.path;
import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.upper;

import java.util.Collection;

/**
 * Base implementation of the AbstractCB
 * 
 * includes the filter and sort methods
 * 
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
	public CB and(CB criteria){

		FilterGroup filterGroup = criteria.build();
		filterGroup.setGroupType(FilterGroupType.AND);

		addQueryFilter(filterGroup);

		return this;
	}

	/**
	 * Filter that sets an OR to the result of a given group of conditions
	 * @param filterBuilder
	 * @return
	 */
	public CB or(CB criteria){

		FilterGroup filterGroup = criteria.build();
		filterGroup.setGroupType(FilterGroupType.OR);
		addQueryFilter(filterGroup);
		return this;
	}

	/**
	 * Filter that sets a NOT to the result of a given group of conditions
	 * @param filterBuilder
	 * @return the builder
	 */
	public CB not(CB criteria){
		FilterGroup filterGroup = criteria.build();
		filterGroup.setGroupType(FilterGroupType.NAND);
		addQueryFilter(filterGroup);

		return this;
	}

	/**
	 * Adds an equal filter to the list
	 * i.e.
	 * 
	 * exp = value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB eq(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.EQ, eb,value));
		return this;
	}

	/**
	 * Adds an equal filter to the list
	 * i.e.
	 * 
	 * expA = expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB eq(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.EQ, eb1,eb2));
		return this;
	}

	/**
	 * Adds an equal filter to the list
	 * i.e.
	 * 
	 * field = value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB eq(String fieldName,Comparable<?> value){
		return eq(path(fieldName),value);
	}

	/**
	 * Adds an equal filter to the list
	 * i.e.
	 * 
	 * field1 = field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB eqProperty(String fieldName1,String fieldName2){
		return eq(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds a not equal filter to the list
	 * i.e.
	 * 
	 * exp != value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB ne(ExpressionBuilder eb,Object value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.NE, eb,value));
		return this;
	}

	/**
	 * Adds a not equal filter to the list
	 * i.e.
	 * 
	 * expA != expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB ne(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.NE, eb1,eb2));
		return this;
	}

	/**
	 * Adds a not equal filter to the list
	 * i.e.
	 * 
	 * field != value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB ne(String fieldName,Comparable<?> value){
		return ne(path(fieldName),value);
	}

	/**
	 * Adds a not equal filter to the list
	 * i.e.
	 * 
	 * field1 != field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB neProperty(String fieldName1,String fieldName2){
		return ne(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds a greater than filter to the list
	 * i.e.
	 * 
	 * exp > value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB gt(ExpressionBuilder eb,Comparable<?> value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GT, eb,value));
		return this;
	}
	
	/**
	 * Adds a greater than filter to the list
	 * i.e.
	 * 
	 * expA > expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB gt(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GT, eb1,eb2));
		return this;
	}
	
	/**
	 * Adds a greater than filter to the list
	 * i.e.
	 * 
	 * field > value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB gt(String fieldName,Comparable<?> value){
		return gt(path(fieldName),value);
	}

	/**
	 * Adds a greater than filter to the list
	 * i.e.
	 * 
	 * field1 > field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB gtProperty(String fieldName1,String fieldName2){
		return gt(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds a greater or equal to filter to the list
	 * i.e.
	 * 
	 * exp >= value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB ge(ExpressionBuilder eb,Comparable<?> value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GE, eb,value));
		return this;
	}

	/**
	 * Adds a greater or equal to filter to the list
	 * i.e.
	 * 
	 * expA >= expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB ge(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.GE, eb1,eb2));
		return this;
	}

	/**
	 * Adds a greater or equal to filter to the list
	 * i.e.
	 * 
	 * field >= value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB ge(String fieldName,Comparable<?> value){
		return ge(path(fieldName),value);
	}

	/**
	 * Adds a greater or equal to filter to the list
	 * i.e.
	 * 
	 * field1 >= field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB geProperty(String fieldName1,String fieldName2){
		return ge(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds a less than filter to the list
	 * i.e.
	 * 
	 * exp < value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB lt(ExpressionBuilder eb,Comparable<?> value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LT, eb,value));
		return this;
	}

	/**
	 * Adds a less than filter to the list
	 * i.e.
	 * 
	 * expA < expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB lt(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LT, eb1,eb2));
		return this;
	}

	/**
	 * Adds a less than filter to the list
	 * i.e.
	 * 
	 * field < value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB lt(String fieldName,Comparable<?> value){
		return lt(path(fieldName),value);
	}

	/**
	 * Adds a less than filter to the list
	 * i.e.
	 * 
	 * field1 < field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB ltProperty(String fieldName1,String fieldName2){
		return lt(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds a less or equal to filter to the list
	 * i.e.
	 * 
	 * exp <= value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB le(ExpressionBuilder eb,Comparable<?> value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LE, eb,value));
		return this;
	}

	/**
	 * Adds a less or equal to filter to the list
	 * i.e.
	 * 
	 * expA <= expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB le(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LE, eb1,eb2));
		return this;
	}

	/**
	 * Adds a less or equal to filter to the list
	 * i.e.
	 * 
	 * field <= value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB le(String fieldName,Comparable<?> value){
		return le(path(fieldName),value);
	}

	/**
	 * Adds a less or equal to filter to the list
	 * i.e.
	 * 
	 * field1 < field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB leProperty(String fieldName1,String fieldName2){
		return le(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds an in filter to the list
	 * i.e.
	 * 
	 * exp in values
	 * 
	 * @param exp - @see {@link ExpressionBuilder}
	 * @param values - the value to be compared
	 * @return the builder
	 */
	public CB in(ExpressionBuilder eb,Collection<?> values){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IN, eb,values));
		return this;
	}

	/**
	 * Adds an in filter to the list
	 * i.e.
	 * 
	 * exp in value
	 * 
	 * @param exp - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB in(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IN, eb1,eb2));
		return this;
	}

	/**
	 * Adds an in filter to the list
	 * i.e.
	 * 
	 * field in values
	 * 
	 * @param field - the field to be compared
	 * @param values - the value to be compared
	 * @return the builder
	 */
	public CB in(String fieldName,Collection<?> values){
		return in(path(fieldName),values);
	}

	/**
	 * Adds a like filter to the list
	 * i.e.
	 * 
	 * exp like value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB like(ExpressionBuilder eb,String value){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LIKE, eb,value));
		return this;
	}

	/**
	 * Adds a like filter to the list
	 * i.e.
	 * 
	 * expA like expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB like(ExpressionBuilder eb1,ExpressionBuilder eb2){
		addQueryFilter(new ConditionFilter(ConditionFilterType.LIKE, eb1,eb2));
		return this;
	}

	/**
	 * Adds a like filter to the list
	 * i.e.
	 * 
	 * field like value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB like(String fieldName,String value){
		return like(path(fieldName),value);
	}

	/**
	 * Adds a like filter to the list
	 * i.e.
	 * 
	 * field1 like field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB likeProperty(String fieldName1,String fieldName2){
		return like(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds a case-insensitive like filter to the list
	 * i.e.
	 * 
	 * exp like value
	 * 
	 * @param eb - the expression builder - @see {@link ExpressionBuilder}
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB ilike(ExpressionBuilder eb,String value){
		return like(upper(eb),value.toUpperCase());
	}
	
	/**
	 * Adds a case insensitive like filter to the list
	 * i.e.
	 * 
	 * expA like expB
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @return the builder
	 */
	public CB ilike(ExpressionBuilder eb1,ExpressionBuilder eb2){
		return like(upper(eb1),upper(eb2));
	}

	/**
	 * Adds a case insensitive like filter to the list
	 * i.e.
	 * 
	 * field like value
	 * 
	 * @param fieldName - the field name to be compared
	 * @param value - the value to be compared
	 * @return the builder
	 */
	public CB ilike(String fieldName,String value){
		return ilike(path(fieldName),value);
	}

	/**
	 * Adds a case insensitive like filter to the list
	 * i.e.
	 * 
	 * field1 like field2
	 * 
	 * @param fieldName1 - the field name to be compared
	 * @param fieldName2 - the other field name to be compared
	 * @return the builder
	 */
	public CB ilikeProperty(String fieldName1,String fieldName2){
		return ilike(path(fieldName1),path(fieldName2));
	}

	/**
	 * Adds a is null filter to the list
	 * 
	 * exp is null
	 * 
	 * @param eb
	 * @return
	 */
	public CB isNull(ExpressionBuilder eb){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IS_NULL, eb));
		return this;
	}

	/**
	 * Adds a is null expression to the list
	 * 
	 * field is null
	 * 
	 * @param fieldName
	 * @return
	 */
	public CB isNull(String fieldName){
		return isNull(path(fieldName));
	}

	/**
	 * Adds a is not null filter to the list
	 * 
	 * exp is not null
	 * 
	 * @param eb
	 * @return
	 */
	public CB isNotNull(ExpressionBuilder eb){
		addQueryFilter(new ConditionFilter(ConditionFilterType.IS_NOT_NULL, eb));
		return this;
	}

	/**
	 * Adds a is not null expression to the list
	 * 
	 * field is not null
	 * 
	 * @param fieldName
	 * @return
	 */
	public CB isNotNull(String fieldName){
		return isNotNull(path(fieldName));
	}

	/**
	 * Adds a between expression to the list
	 * 
	 * exp between low and high
	 * 
	 * @param eb - @see {@link ExpressionBuilder}
	 * @param lowValue - the low value
	 * @param highValue - the high value
	 * @return
	 */
	public CB between(ExpressionBuilder eb,Comparable<?> lowValue,Comparable<?> highValue){
		addQueryFilter(new ConditionFilter(ConditionFilterType.BETWEEN, eb,lowValue,highValue));
		return this;
	}

	/**
	 * Adds a between expression to the list
	 * 
	 * expA between expB and expC
	 * 
	 * @param eb1 - @see {@link ExpressionBuilder}
	 * @param eb2 - @see {@link ExpressionBuilder}
	 * @param eb3 - @see {@link ExpressionBuilder}
	 * @return
	 */
	public CB between(ExpressionBuilder eb1, ExpressionBuilder eb2, ExpressionBuilder eb3){
		addQueryFilter(new ConditionFilter(ConditionFilterType.BETWEEN, eb1,eb2,eb3));
		return this;
	}

	/**
	 * Adds a between expression to the list
	 * 
	 * field between low and high
	 * 
	 * @param fieldName - the field to be compared
	 * @param lowValue - the low value
	 * @param highValue - the high value
	 * @return
	 */
	public CB between(String fieldName,Comparable<?> lowValue,Comparable<?> highValue){
		return between(path(fieldName),lowValue,highValue);
	}

	/**
	 * Filter that joins conditions from a related Entity Object
	 * same as join(fieldName,JoinType.INNER,filterBuilder);
	 * @param fieldName the entity related property
	 * @param criteria the related filter builder
	 * @return the generated condition filter
	 */
	public CB join(String fieldName,CB criteria){
		return join(fieldName, JoinType.INNER,criteria);
	}


	/**
	 * Filter that joins conditions from a related Entity Object
	 * 
	 * @param fieldName - the field to join by
	 * @param joinType - the join type
	 * @param criteria - the join criteria
	 * @return
	 */
	public CB join(String fieldName,JoinType joinType,CB criteria){
		addQueryFilter(new ConditionFilter(ConditionFilterType.JOIN, new Object[]{ fieldName,criteria,joinType }));
		return this;
	}

	
	/**
	 * Adds a sort to the criteria
	 * 
	 * same as sort(fieldName,ASC)
	 * 
	 * @param fieldName - the field to sort by
	 * @return
	 */
	public CB sort(String fieldName){
		return sort(fieldName, Order.ASC);
	}
	
	/**
	 * Adds a sort to the criteria
	 * 
	 * Currently only works for root element fields
	 * 
	 * @param fieldName - the field to sort by
	 * @param order - the direction
	 * @return
	 */
	public CB sort(String fieldName,Order order){
		QuerySort querySort = new QuerySort(fieldName, order);
		getSortList().add(querySort);
		return this;
	}

}
