package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
/**
 * Expression Builder, to generate multiple expressions having a criteriaBuilder and a root path
 * 
 * For example to build the column expression from an entity (entity.myProperty)
 * 
 * @author Humberto Guerrero Jaime
 */
public interface ExpressionBuilder<E extends Expression<?>> {

    E build(CriteriaBuilder criteriaBuilder, Path<?> path);

}
