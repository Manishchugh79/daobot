package com.github.hguerrerojaime.daobot.core.builders.predicate;

import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.utils.PredicateBuilderUtils;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;

@SuppressWarnings({"rawtypes","unchecked"})
public class EqPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                                                                               CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                                                                               Class<T> entityClass) {

        Object[] args = conditionFilter.getArgs();

        if (PredicateBuilderUtils.allArgsAreExpressionBuilders(args)) {

            ExpressionBuilder eb1 = (ExpressionBuilder) args[0];
            ExpressionBuilder eb2 = (ExpressionBuilder) args[1];

            return criteriaBuilder.equal(eb1.build(criteriaBuilder, eoPath), eb2.build(criteriaBuilder, eoPath));

        } else {

            ExpressionBuilder eb1 = (ExpressionBuilder) args[0];
            Comparable value = (Comparable) args[1];

            return criteriaBuilder.equal(eb1.build(criteriaBuilder,eoPath),value);

        }
    }

}
