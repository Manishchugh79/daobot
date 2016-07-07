package com.github.hguerrerojaime.daobot.core.builders.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
/**
 * Created by G834244 on 06/02/2016.
 */
public interface ExpressionBuilder<E extends Expression<?>> {

    E build(CriteriaBuilder criteriaBuilder, Path<?> path);

}
