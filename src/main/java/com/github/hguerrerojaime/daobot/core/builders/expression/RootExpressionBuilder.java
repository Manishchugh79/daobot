package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Builds a root expression
 * 
 * @author Humberto Guerrero Jaime
 */
public class RootExpressionBuilder<X> implements ExpressionBuilder<Root<X>> {

	@SuppressWarnings("unchecked")
	@Override
	public Root<X> build(CriteriaBuilder criteriaBuilder, Path<?> path) {
		return (Root<X>) path;
	}

}
