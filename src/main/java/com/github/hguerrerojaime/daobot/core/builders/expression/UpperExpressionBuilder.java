package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * Created by G834244 on 06/02/2016.
 */
public class UpperExpressionBuilder implements ExpressionBuilder<Expression<String>> {

    private ExpressionBuilder expressionBuilder;

    public UpperExpressionBuilder(ExpressionBuilder expressionBuilder) {
        this.expressionBuilder = expressionBuilder;
    }

    @Override
    public Expression<String> build(CriteriaBuilder criteriaBuilder, Path path) {
        return criteriaBuilder.upper(getExpressionBuilder().build(criteriaBuilder, path));
    }

    public ExpressionBuilder getExpressionBuilder() {
        return expressionBuilder;
    }
}
