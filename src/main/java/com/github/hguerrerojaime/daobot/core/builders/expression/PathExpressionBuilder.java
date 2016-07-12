package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

/**
 * Builds a path expression
 * 
 * @author Humberto Guerrero Jaime
 */
public class PathExpressionBuilder implements ExpressionBuilder<Path<?>> {

    private String fieldPath;

    public PathExpressionBuilder(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    @Override
    public Path<?> build(CriteriaBuilder criteriaBuilder, Path<?> path) {
        return path.get(getFieldPath());
    }

    public String getFieldPath() {
        return fieldPath;
    }
}
