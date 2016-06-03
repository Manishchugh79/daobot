package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * Created by G834244 on 06/02/2016.
 */
public class ConcatExpressionBuilder implements ExpressionBuilder<Expression<String>> {


    private ExpressionBuilder[] expressionBuilders;

    public ConcatExpressionBuilder(ExpressionBuilder... expressionBuilders) {
        this.expressionBuilders = expressionBuilders;
    }

    @Override
    public Expression<String> build(CriteriaBuilder criteriaBuilder, Path path) {
        return concatExpressions(criteriaBuilder,path);
    }

    private Expression<String> concatExpressions(CriteriaBuilder criteriaBuilder,Path path) {

        Expression nestedExpression = null;

        for (ExpressionBuilder expressionBuilder : expressionBuilders) {

            Expression builtExpression = expressionBuilder.build(criteriaBuilder,path);

            if (nestedExpression == null) {
                nestedExpression = builtExpression;
            } else {
                nestedExpression = criteriaBuilder.concat(nestedExpression,builtExpression);
            }

        }

        return nestedExpression;

    }


}
