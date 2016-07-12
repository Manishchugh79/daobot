package com.github.hguerrerojaime.daobot.core.builders.predicate;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.utils.PredicateBuilderUtils;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * Builds a in predicate
 * 
 * @author Humberto Guerrero Jaime
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class InPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(
            ConditionFilter conditionFilter,
            CriteriaBuilder criteriaBuilder, Path<T> eoPath,
            Class<T> entityClass) {

        Object[] args = conditionFilter.getArgs();

        if (PredicateBuilderUtils.allArgsAreExpressionBuilders(args)) {

            ExpressionBuilder eb1 = (ExpressionBuilder) args[0];
            ExpressionBuilder eb2 = (ExpressionBuilder) args[1];

            return eb1.build(criteriaBuilder, eoPath).in(eb2.build(criteriaBuilder, eoPath));

        } else {

            ExpressionBuilder eb1 = (ExpressionBuilder) args[0];
            Collection<?> value = (Collection<?>) args[1];

            return eb1.build(criteriaBuilder,eoPath).in(value);

        }

    }

}
