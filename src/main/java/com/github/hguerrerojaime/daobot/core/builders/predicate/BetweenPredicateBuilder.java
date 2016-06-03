package com.github.hguerrerojaime.daobot.core.builders.predicate;

import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.utils.PredicateBuilderUtils;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;

/**
 * Created by G834244 on 06/02/2016.
 */
public class BetweenPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                                                                               CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                                                                               Class<T> entityClass) {
        Object[] args = conditionFilter.getArgs();

        if (PredicateBuilderUtils.allArgsAreExpressionBuilders(args)) {

            ExpressionBuilder exp1 = (ExpressionBuilder) args[0];
            ExpressionBuilder exp2 = (ExpressionBuilder) args[1];
            ExpressionBuilder exp3 = (ExpressionBuilder) args[2];

            return criteriaBuilder.between(
                    exp1.build(criteriaBuilder,eoPath),
                    exp2.build(criteriaBuilder,eoPath),
                    exp3.build(criteriaBuilder,eoPath)
            );
        } else {

            ExpressionBuilder exp1 = (ExpressionBuilder) args[0];
            Comparable lowVal = (Comparable) args[1];
            Comparable highVal = (Comparable) args[2];

            return criteriaBuilder.between(exp1.build(criteriaBuilder,eoPath),lowVal,highVal);
        }

    }



}
