package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.github.hguerrerojaime.daobot.eo.EntityObject;

public interface ConditionPredicateBuilder {

    <T extends EntityObject<K>, K extends Serializable> Predicate build(
            ConditionFilter conditionFilter, CriteriaBuilder criteriaBuilder,
            Path<T> eoPath, Class<T> entityClass);

}
