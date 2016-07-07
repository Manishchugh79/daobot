package com.github.hguerrerojaime.daobot.core.builders.predicate;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.utils.PredicateBuilderUtils;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * Created by G834244 on 06/02/2016.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class LtPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(
            ConditionFilter conditionFilter,
            CriteriaBuilder criteriaBuilder, Path<T> eoPath,
            Class<T> entityClass) {
        Object[] args = conditionFilter.getArgs();

        if (PredicateBuilderUtils.allArgsAreExpressionBuilders(args)) {

            ExpressionBuilder eb1 = (ExpressionBuilder) args[0];
            ExpressionBuilder eb2 = (ExpressionBuilder) args[1];

            return criteriaBuilder.lessThan(eb1.build(criteriaBuilder,eoPath),eb2.build(criteriaBuilder,eoPath));

        } else {

            ExpressionBuilder eb1 = (ExpressionBuilder) args[0];
            Comparable value = (Comparable) args[1];

            return criteriaBuilder.lessThan(eb1.build(criteriaBuilder,eoPath),value);

        }
    }

}
