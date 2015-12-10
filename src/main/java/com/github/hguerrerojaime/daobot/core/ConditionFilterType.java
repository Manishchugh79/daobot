package com.github.hguerrerojaime.daobot.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import com.github.hguerrerojaime.daobot.eo.EntityObject;
import com.github.hguerrerojaime.daobot.utils.Utils;

@SuppressWarnings({"unchecked","rawtypes"})
public enum ConditionFilterType {
    BETWEEN("between",3,new ConditionPredicateBuilder(){

        
        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            
            Expression betweenField = eoPath.get(String
                    .valueOf(conditionFilter.getArgs()[0]));

            Object betweenLowValArg = conditionFilter.getArgs()[1];
            Object betweenHighValArg = conditionFilter.getArgs()[2];

            Comparable betweenLowVal = (Comparable) betweenLowValArg;
            Comparable betweenHighVal = (Comparable) betweenHighValArg;

            return criteriaBuilder.between(betweenField, betweenLowVal,
                    betweenHighVal);
        }
        
    }),
    EQ("eq",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            
            String eqFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression<?> eqField = eoPath.get(eqFieldName);
            Object eqValue = conditionFilter.getArgs()[1];
        
            return criteriaBuilder.equal(eqField, eqValue);
        }
        
    }),
    EQ_PROPERTY("eqProperty",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String eqPropertyFieldName1 = String
                    .valueOf(conditionFilter.getArgs()[0]);
            String eqPropertyFieldName2 = String
                    .valueOf(conditionFilter.getArgs()[1]);

            Expression<?> eqPropertyField1 = eoPath
                    .get(eqPropertyFieldName1);

            Expression<?> eqPropertyField2 = eoPath
                    .get(eqPropertyFieldName2);

            return criteriaBuilder.equal(eqPropertyField1, eqPropertyField2);
        }
        
    }),
    GT("gt",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
           
            String gtFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression gtField = eoPath.get(gtFieldName);
            Comparable gtValue = (Comparable) conditionFilter.getArgs()[1];

            return criteriaBuilder.greaterThan(gtField, gtValue);
        }
        
    }),
    GT_PROPERTY("gtProperty",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
            String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

            Expression field1 = eoPath.get(fieldName1);
            Expression field2 = eoPath.get(fieldName2);

            return criteriaBuilder.greaterThan(field1, field2);
        }
        
    }),
    GE("ge",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String geFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression geField = eoPath.get(geFieldName);
            Comparable geValue = (Comparable) conditionFilter.getArgs()[1];

            return criteriaBuilder.greaterThanOrEqualTo(geField, geValue);
        }
        
    }),
    GE_PROPERTY("geProperty",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
            String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

            Expression field1 = eoPath.get(fieldName1);

            Expression field2 = eoPath.get(fieldName2);

            return criteriaBuilder.greaterThanOrEqualTo(field1, field2);
        }
        
    }),
    ID_EQ("idEq",1,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            
            Field idEqReflectField = Utils.getIdField(entityClass);

            if (idEqReflectField == null) {
                throw new IllegalArgumentException(
                        "The entity object does not have a field with the @Id annotation");
            }

            String idEqFieldName = idEqReflectField.getName();
            Path<Serializable> idEqField = eoPath.get(idEqFieldName);

            Object idEqValue = conditionFilter.getArgs()[0];

            return criteriaBuilder.equal(idEqField, idEqValue);
        }
        
    }),
    ID_NE("idNe",1,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            Field idNeReflectField = Utils.getIdField(entityClass);

            if (idNeReflectField == null) {
                throw new IllegalStateException(
                        "The entity object does not have a field with the @Id annotation");
            }

            String idNeFieldName = idNeReflectField.getName();
            Path<Serializable> idNeField = eoPath.get(idNeFieldName);

            K idNeValue = (K) conditionFilter.getArgs()[0];

            return criteriaBuilder.notEqual(idNeField, idNeValue);
        }}),
    ILIKE("ilike",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String ilikeFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression<String> ilikeField = eoPath.get(ilikeFieldName);

            String ilikeValue = (String) conditionFilter.getArgs()[1];

            return criteriaBuilder.like(criteriaBuilder.upper(ilikeField),
                    ilikeValue.toUpperCase());
        }
        
    }),
    IN("in",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String inFieldName = String.valueOf(conditionFilter.getArgs()[0]);
            Expression<? extends Comparable<?>> inField = eoPath.get(inFieldName);

            Collection<?> inValue = (Collection<?>) conditionFilter.getArgs()[1];

            return inField.in(inValue);
        }}),
    IS_EMPTY("isEmpty",1,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String fieldName = String.valueOf(conditionFilter.getArgs()[0]);
            
            Expression field = eoPath.get(fieldName);
            
            return criteriaBuilder.isEmpty(field);
        }}),
    IS_NOT_EMPTY("isNotEmpty",1,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String fieldName = String.valueOf(conditionFilter.getArgs()[0]);
            
            Expression field = eoPath.get(fieldName);
            
            return criteriaBuilder.isNotEmpty(field);
        }
        
    }),
    IS_NULL("isNull",1,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String isNullFieldName = String.valueOf(conditionFilter.getArgs()[0]);
            Expression<? extends Comparable<?>> isNullField = eoPath
                    .get(isNullFieldName);

            return criteriaBuilder.isNull(isNullField);
        }
        
    }),
    IS_NOT_NULL("isNotNull",1,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String isNotNullFieldName = String
                    .valueOf(conditionFilter.getArgs()[0]);
            Expression<? extends Comparable<?>> isNotNullField = eoPath
                    .get(isNotNullFieldName);

            return criteriaBuilder.isNotNull(isNotNullField);
        }
        
    }),
    JOIN("join",3,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String joinFieldName = String.valueOf(conditionFilter.getArgs()[0]);
            AbstractQueryBuilder filterBuilder = (AbstractQueryBuilder) conditionFilter.getArgs()[1];
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
        
    }),
    LIKE("like",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String likeFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression<String> likeField = eoPath.get(likeFieldName);
            String likeValue = (String) conditionFilter.getArgs()[1];

            return criteriaBuilder.like(likeField, likeValue);
        }
        
    }),
    LT("lt",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String ltFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression ltField = eoPath.get(ltFieldName);
            Comparable ltValue = (Comparable) conditionFilter.getArgs()[1];

            return criteriaBuilder.lessThan(ltField, ltValue);
        }
        
    }),
    LT_PROPERTY("ltProperty",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
            String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

            Expression field1 = eoPath.get(fieldName1);

            Expression field2 = eoPath.get(fieldName2);

            return criteriaBuilder.lessThan(field1, field2);
        }
        
    }),
    LE("le",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String leFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression leField = eoPath.get(leFieldName);
            Comparable leValue = (Comparable) conditionFilter.getArgs()[1];

            return criteriaBuilder.lessThanOrEqualTo(leField, leValue);
        }
        
    }),
    LE_PROPERTY("leProperty",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
            String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

            Expression field1 = eoPath.get(fieldName1);

            Expression field2 = eoPath.get(fieldName2);

            return criteriaBuilder.lessThanOrEqualTo(field1, field2);
        }
        
    }),
    NE("ne",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String neFieldName = String.valueOf(conditionFilter.getArgs()[0]);

            Expression<?> neField = eoPath.get(neFieldName);
            Object neValue = conditionFilter.getArgs()[1];

            return criteriaBuilder.notEqual(neField, neValue);
        }
        
    }),
    NE_PROPERTY("neProperty",2,new ConditionPredicateBuilder(){

        @Override
        public <T extends EntityObject<K>, K extends Serializable> Predicate build(
                ConditionFilter conditionFilter,
                CriteriaBuilder criteriaBuilder, Path<T> eoPath,
                Class<T> entityClass) {
            String fieldName1 = String.valueOf(conditionFilter.getArgs()[0]);
            String fieldName2 = String.valueOf(conditionFilter.getArgs()[1]);

            Expression<?> field1 = eoPath.get(fieldName1);

            Expression<?> field2 = eoPath.get(fieldName2);

            return criteriaBuilder.notEqual(field1, field2);
        }
        
    });
    
    private String code;
    private int numArgs;
    private ConditionPredicateBuilder pb;
    
    private ConditionFilterType(String code,int numArgs,ConditionPredicateBuilder pb){
        this.code = code;
        this.numArgs = numArgs;
        this.pb = pb;
    }
    
    public String getCode(){
        return code;
    }
    
    public int getNumArgs(){
        return numArgs;
    }
    
    public ConditionPredicateBuilder getBuilder(){
        return pb;
    }
    
    public static ConditionFilterType byCode(String code){
        
        ConditionFilterType result = null;
        
        for(ConditionFilterType type : values()){
            
            if(type.getCode().equals(code)){
                result = type;
                break;
            }
            
        }
        
        return result;
        
    }
}
