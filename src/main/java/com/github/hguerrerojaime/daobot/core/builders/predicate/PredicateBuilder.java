package com.github.hguerrerojaime.daobot.core.builders.predicate;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.github.hguerrerojaime.daobot.core.ConditionFilter;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * Builds a predicate
 * 
 * @author Humberto Guerrero Jaime
 */
public interface PredicateBuilder {

    /**
     * Build a predicate with the given arguments
     * 
     * @param conditionFilter - The condition filter object @see {@link ConditionFilter}}
     * @param criteriaBuilder - The criteria builder object @see {@link CriteriaBuilder}
     * @param eoPath - The entity object path {@see Path}
     * @param entityClass - The entity object path {@see EntityObject}
     * @return the build predicate
     */
    <T extends EntityObject<K>, K extends Serializable> Predicate build(
            ConditionFilter conditionFilter, CriteriaBuilder criteriaBuilder,
            Path<T> eoPath, Class<T> entityClass);

}
