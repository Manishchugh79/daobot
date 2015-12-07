package com.github.hguerrerojaime.daobot.core;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public enum FilterGroupType {
    AND(new GroupPredicateBuilder(){

        @Override
        public Predicate build(CriteriaBuilder criteriaBuilder,
                List<Predicate> criteriaList) {
            return criteriaBuilder.and(criteriaList.toArray(new Predicate[0]));
            
        }}),
    OR(new GroupPredicateBuilder(){

        @Override
        public Predicate build(CriteriaBuilder criteriaBuilder,
                List<Predicate> criteriaList) {
            return criteriaBuilder.or(criteriaList.toArray(new Predicate[0]));
        }}),
    NAND(new GroupPredicateBuilder(){

        @Override
        public Predicate build(CriteriaBuilder criteriaBuilder,
                List<Predicate> criteriaList) {
            return criteriaBuilder.not(criteriaBuilder.and(criteriaList
                    .toArray(new Predicate[0])));
        }}),
    NOR(new GroupPredicateBuilder(){

        @Override
        public Predicate build(CriteriaBuilder criteriaBuilder,
                List<Predicate> criteriaList) {
            return criteriaBuilder.not(criteriaBuilder.or(criteriaList
                    .toArray(new Predicate[0])));
        }});
    
    private GroupPredicateBuilder gpb;
    
    private FilterGroupType(GroupPredicateBuilder gpb){
        this.gpb = gpb;
    }
    
    public GroupPredicateBuilder getBuilder(){
        return gpb;
    }
}
