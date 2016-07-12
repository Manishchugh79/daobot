package com.github.hguerrerojaime.daobot.core;

/**
 * Condition Filter Class
 * 
 * is added to the filter list
 * 
 * @author Humberto Guerrero Jaime
 *
 */
public class ConditionFilter implements QueryFilter {

	private ConditionFilterType conditionType;
	private Object[] args;
	
	public ConditionFilter(ConditionFilterType conditionType,Object... args) {
		super();
		this.conditionType = conditionType;
		this.args = args;
	}

	/**
	 * @return the condition type
	 */
	public ConditionFilterType  getConditionType() {
		return conditionType;
	}


	/**
	 * @return the condition arguments
	 */
	public Object[] getArgs() {
		return args;
	}
	

}
