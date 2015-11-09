package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.criteria.JoinType;

import com.github.hguerrerojaime.daobot.core.ConditionFilter.Type;

/**
 * @author Humberto Guerrero Jaime
 *
 */
@SuppressWarnings("rawtypes")
public class FB extends AbstracQueryBuilder{
	
	private FilterGroup filterBuild;
	
	/**
	 * Constructor with a default encapsulating AND filterGroupType
	 * 
	 * @param filterGroupType
	 */
	public FB() {
		this(FilterGroup.Type.AND);
	}
	
	/**
	 * Constructor indicating the encapsulating filterGroupType
	 * 
	 * @param filterGroupType
	 */
	public FB(FilterGroup.Type filterGroupType){
		super(filterGroupType);
		
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
	public FB and(FB filterBuilder){
		
		FilterGroup filterGroup = filterBuilder.build();
		filterGroup.setGroupType(FilterGroup.Type.AND);
		
		addQueryFilter(filterGroup);
		
		return this;
	}
	
	/**
	 * Filter that sets an OR to the result of a given group of conditions
	 * @param filterBuilder
	 * @return
	 */
	public FB or(FB filterBuilder){
		
		FilterGroup filterGroup = filterBuilder.build();
		filterGroup.setGroupType(FilterGroup.Type.AND);
		
		filterGroup.setGroupType(FilterGroup.Type.OR);
		addQueryFilter(filterGroup);
		return this;
	}
	
	/**
	 * Filter that sets a NOT to the result of a given group of conditions
	 * @param filterBuilder
	 * @return
	 */
	public FB not(FB filterBuilder){
		
		FilterGroup filterGroup = filterBuilder.build();
		
		if(filterGroup.getGroupType().equals(FilterGroup.Type.OR)){
			filterGroup.setGroupType(FilterGroup.Type.NOR);
		}else if(filterGroup.getGroupType().equals(FilterGroup.Type.AND)){
			filterGroup.setGroupType(FilterGroup.Type.NAND);
		}else if(filterGroup.getGroupType().equals(FilterGroup.Type.NAND)){
			filterGroup.setGroupType(FilterGroup.Type.AND);
		}else if(filterGroup.getGroupType().equals(FilterGroup.Type.NOR)){
			filterGroup.setGroupType(FilterGroup.Type.OR);
		}
		
		addQueryFilter(filterGroup);
		
		return this;
	}
	
	
	/**
	 * Filter that checks if the value for the given fieldName is EQUAL
	 * to the given value
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB eq(String fieldName,Object value){
		addQueryFilter(new ConditionFilter(Type.EQ, new Object[]{ fieldName,value }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName1 is EQUAL
	 * to the given fieldName2
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB eqProperty(String fieldName1,String fieldName2){
		addQueryFilter(new ConditionFilter(Type.EQ_PROPERTY, new Object[]{ fieldName1,fieldName2 }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName is NOT EQUAL
	 * to the given value
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB ne(String fieldName,Comparable<?> value){
		addQueryFilter(new ConditionFilter(Type.NE, new Object[]{ fieldName,value }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName1 is NOT EQUAL
	 * to the given fieldName2
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB neProperty(String fieldName1,String fieldName2){
		addQueryFilter(new ConditionFilter(Type.NE_PROPERTY, new Object[]{ fieldName1,fieldName2 }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName is greater
	 * than the given value
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB gt(String fieldName,Comparable value){
		addQueryFilter(new ConditionFilter(Type.GT, new Object[]{ fieldName,value }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName1 is greater
	 * than the given fieldName2
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB gtProperty(String fieldName1,String fieldName2){
		addQueryFilter(new ConditionFilter(Type.GT_PROPERTY, new Object[]{ fieldName1,fieldName2 }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName is greater or equal
	 * than the given value
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB ge(String fieldName,Comparable value){
		addQueryFilter(new ConditionFilter(Type.GE, new Object[]{ fieldName,value }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName1 is greater or equal
	 * than the given fieldName2
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB geProperty(String fieldName1,String fieldName2){
		addQueryFilter(new ConditionFilter(Type.GE_PROPERTY, new Object[]{ fieldName1,fieldName2 }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName is less
	 * than the given value
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB lt(String fieldName,Comparable value){
		addQueryFilter(new ConditionFilter(Type.LT, new Object[]{ fieldName,value }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName1 is less
	 * than the given fieldName2
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB ltProperty(String fieldName1,String fieldName2){
		addQueryFilter(new ConditionFilter(Type.LT_PROPERTY, new Object[]{ fieldName1,fieldName2 }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName is less or equal
	 * than the given value
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB le(String fieldName,Comparable value){
		addQueryFilter(new ConditionFilter(Type.LE, new Object[]{ fieldName,value }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName1 is less or equal
	 * than the given fieldName2
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB leProperty(String fieldName1,String fieldName2){
		addQueryFilter(new ConditionFilter(Type.LE_PROPERTY, new Object[]{ fieldName1,fieldName2 }));
		return this;
	}
	
	/**
	 * Filter that checks that the value for the given fieldName
	 * is in the given collection of values
	 * @param fieldName
	 * @param values
	 * @return
	 */
	public FB in(String fieldName,Collection<?> values){
		addQueryFilter(new ConditionFilter(Type.IN, new Object[]{ fieldName,values }));
		return this;
	}
	
	/**
	 * Filter that checks if the value for the given fieldName is like the given value
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB like(String fieldName,String value){
		addQueryFilter(new ConditionFilter(Type.LIKE, new Object[]{ fieldName,value }));
		return this;
	}
	
	/**
	 * Filter same as like but case insensitive
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public FB ilike(String fieldName,String value){
		addQueryFilter(new ConditionFilter(Type.ILIKE, new Object[]{ fieldName,value }));
		return this;
	}
	
	
	
	/**
	 * Filter that checks that the value for the given fieldName is NULL
	 * @param fieldName
	 * @return
	 */
	public FB isNull(String fieldName){
		addQueryFilter(new ConditionFilter(Type.IS_NULL, new Object[] {fieldName}));
		return this;
	}
	
	/**
	 * Filter that checks that the value for the given fieldName is NOT NULL
	 * @param fieldName
	 * @return
	 */
	public FB isNotNull(String fieldName){
		addQueryFilter(new ConditionFilter(Type.IS_NOT_NULL, new Object[] {fieldName}));
		return this;
	}
	
	/**
	 * Filter that checks that the value for the given fieldName is between lowValue and highValue
	 * 
	 * @param fieldName
	 * @param lowValue
	 * @param highValue
	 * @return
	 */
	public FB between(String fieldName,Comparable lowValue,Comparable highValue){
		addQueryFilter(new ConditionFilter(Type.BETWEEN, new Object[]{ fieldName,lowValue,highValue }));
		return this;
	}
	
	/**
	 * Filter that checks if the Entity Primary Key is Equal to the given value
	 * Be advised, this method works only when only one Field has the @Id annotation
	 * @param id
	 * @return
	 */
	public <K extends Serializable> FB idEq(K id){
		addQueryFilter(new ConditionFilter(Type.ID_EQ, new Object[]{ id }));
		return this;
	}
	
	/**
	 * Filter that checks if the Entity Primary Key is NOT Equal to the given value
	 * Be advised, this method works only when only one Field has the @Id annotation
	 * @param id
	 * @return
	 */
	public <K extends Serializable> FB idNe(K id){
		addQueryFilter(new ConditionFilter(Type.ID_NE, new Object[]{ id }));
		return this;
	}
	
	/**
	 * Filter that joins conditions from a related Entity Object
	 * same as join(fieldName,JoinType.INNER,filterBuilder);
	 * @param fieldName the entity related property
	 * @param filterBuilder the related filter builder
	 * @return the generated condition filter
	 */
	public FB join(String fieldName,FB filterBuilder){
		return join(fieldName,JoinType.INNER,filterBuilder);
	}
	
	
	/**
	 * @param fieldName
	 * @param joinType
	 * @param filterBuilder
	 * @return
	 */
	public FB join(String fieldName,JoinType joinType,FB filterBuilder){
		addQueryFilter(new ConditionFilter(Type.JOIN, new Object[]{ fieldName,filterBuilder,joinType }));
		return this;
	}
	
}
