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
public class EqPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                                                                               CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                                                                               Class<T> entityClass) {

        ExpressionBuilder pathExpressionBuilder = (ExpressionBuilder) conditionFilter.getArgs()[0];
        Expression<?> eqField = pathExpressionBuilder.build(criteriaBuilder,eoPath);
        Object eqValue = conditionFilter.getArgs()[1];

        return criteriaBuilder.equal(eqField, eqValue);
    }

}
