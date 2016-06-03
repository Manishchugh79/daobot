package com.github.hguerrerojaime.daobot.core.builders.predicate;

import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;

/**
 * Created by G834244 on 06/02/2016.
 */
public class IsNotNullPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(
            ConditionFilter conditionFilter,
            CriteriaBuilder criteriaBuilder, Path<T> eoPath,
            Class<T> entityClass) {
        ExpressionBuilder expressionBuilder = (ExpressionBuilder) conditionFilter.getArgs()[0];

        Expression field = expressionBuilder.build(criteriaBuilder, eoPath);

        return criteriaBuilder.isNotNull(field);
    }

}
