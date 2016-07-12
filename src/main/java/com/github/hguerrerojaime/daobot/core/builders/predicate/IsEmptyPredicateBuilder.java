package com.github.hguerrerojaime.daobot.core.builders.predicate;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * Builds a isEmpty predicate
 * 
 * @author Humberto Guerrero Jaime
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class IsEmptyPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(
            ConditionFilter conditionFilter,
            CriteriaBuilder criteriaBuilder, Path<T> eoPath,
            Class<T> entityClass) {

        ExpressionBuilder expressionBuilder = (ExpressionBuilder) conditionFilter.getArgs()[0];

        Expression field = expressionBuilder.build(criteriaBuilder,eoPath);

        return criteriaBuilder.isEmpty(field);
    }

}
