package com.github.hguerrerojaime.daobot.core;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * The Group Predicate Builder to encapsulate the filters
 * 
 * @author Humberto Guerrero Jaime
 *
 */
public interface GroupPredicateBuilder {

    Predicate build(CriteriaBuilder criteriaBuilder, List<Predicate> criteriaList);
    
}
