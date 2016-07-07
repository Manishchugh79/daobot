package com.github.hguerrerojaime.daobot.core.builders.predicate;

import com.github.hguerrerojaime.daobot.core.AbstractCB;
import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.core.QueryFilterGenerator;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by G834244 on 06/02/2016.
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

        Root<T> eoRoot = (Root<T>) eoPath;

        Path joinRoot = eoRoot.join(joinFieldName,joinType);

        Field joinField = null;

        try {
            joinField = entityClass.getDeclaredField(joinFieldName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Join field not found",e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException("Join field not accessible",e);
        }


        QueryFilterGenerator queryFilterBuilder = new QueryFilterGenerator(
                joinRoot, (Class<?>) joinField.getType(), criteriaBuilder);

        return queryFilterBuilder.buildAndEncapsulateFilters(filterBuilder.build());
    }

}
