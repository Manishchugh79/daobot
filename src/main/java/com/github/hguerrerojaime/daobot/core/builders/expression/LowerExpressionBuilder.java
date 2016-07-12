package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * Builds a lower expression
 * 
 * @author Humberto Guerrero Jaime
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class LowerExpressionBuilder implements ExpressionBuilder<Expression<String>> {

    
	private ExpressionBuilder expressionBuilder;

    public LowerExpressionBuilder(ExpressionBuilder expressionBuilder) {
        this.expressionBuilder = expressionBuilder;
    }

    @Override
    public Expression<String> build(CriteriaBuilder criteriaBuilder, Path<?> path) {
        return criteriaBuilder.lower(getExpressionBuilder().build(criteriaBuilder, path));
    }

    public ExpressionBuilder getExpressionBuilder() {
        return expressionBuilder;
    }

}
