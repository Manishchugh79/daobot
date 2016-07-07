package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * Created by G834244 on 06/02/2016.
 */
public class LiteralExpressionBuilder<T> implements ExpressionBuilder<Expression<T>> {

    private T literal;

    public LiteralExpressionBuilder(T literal) {
        this.literal = literal;
    }

    @Override
    public Expression<T> build(CriteriaBuilder criteriaBuilder, Path<?> path) {
        return criteriaBuilder.literal(getLiteral());
    }

    public T getLiteral() {
        return literal;
    }

}
