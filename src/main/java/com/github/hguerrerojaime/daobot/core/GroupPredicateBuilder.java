package com.github.hguerrerojaime.daobot.core;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public interface GroupPredicateBuilder {

    Predicate build(CriteriaBuilder criteriaBuilder, List<Predicate> criteriaList);
    
}
