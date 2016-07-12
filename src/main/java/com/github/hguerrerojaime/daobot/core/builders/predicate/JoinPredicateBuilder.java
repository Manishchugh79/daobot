package com.github.hguerrerojaime.daobot.core.builders.predicate;

import com.github.hguerrerojaime.daobot.core.AbstractCB;
import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.QueryFilterGenerator;
import com.github.hguerrerojaime.daobot.eo.EntityObject;


import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * Builds a join predicate
 * 
 * @author Humberto Guerrero Jaime
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class JoinPredicateBuilder implements PredicateBuilder {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Predicate build(
            ConditionFilter conditionFilter,
            CriteriaBuilder criteriaBuilder, Path<T> eoPath,
            Class<T> entityClass) {
        String joinFieldName = String.valueOf(conditionFilter.getArgs()[0]);
        AbstractCB filterBuilder = (AbstractCB) conditionFilter.getArgs()[1];
        JoinType joinType = (JoinType) conditionFilter.getArgs()[2];

        From joinRoot = (From) eoPath;

        Path joinPath = joinRoot.join(joinFieldName,joinType);

        Field joinField = null;

        try {
            joinField = entityClass.getDeclaredField(joinFieldName);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalArgumentException("Join field not found",e);
        }


        QueryFilterGenerator queryFilterBuilder = new QueryFilterGenerator(
                joinPath, (Class<?>) joinField.getType(), criteriaBuilder);

        return queryFilterBuilder.buildAndEncapsulateFilters(filterBuilder.build());
    }

}
