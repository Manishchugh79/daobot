package com.github.hguerrerojaime.daobot.core;

/**
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

	public ConditionFilterType  getConditionType() {
		return conditionType;
	}


	public Object[] getArgs() {
		return args;
	}
	

}
