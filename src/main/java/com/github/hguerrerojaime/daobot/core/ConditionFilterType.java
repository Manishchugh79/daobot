package com.github.hguerrerojaime.daobot.core;

import com.github.hguerrerojaime.daobot.core.builders.predicate.BetweenPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.EqPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.GePredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.GtPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.InPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.IsEmptyPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.IsNotEmptyPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.IsNotNullPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.IsNullPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.JoinPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.LePredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.LikePredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.LtPredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.NePredicateBuilder;
import com.github.hguerrerojaime.daobot.core.builders.predicate.PredicateBuilder;

/**
 * Condition Filter Types with their own PredicateBuilders
 * 
 * @author Humberto Guerrero Jaime
 *
 */
public enum ConditionFilterType {
    BETWEEN("between",3,new BetweenPredicateBuilder()),
    EQ("eq",2,new EqPredicateBuilder()),
    GT("gt",2,new GtPredicateBuilder()),
    GE("ge",2,new GePredicateBuilder()),
    IN("in",2,new InPredicateBuilder()),
    IS_EMPTY("isEmpty",1,new IsEmptyPredicateBuilder()),
    IS_NOT_EMPTY("isNotEmpty",1,new IsNotEmptyPredicateBuilder()),
    IS_NULL("isNull",1,new IsNullPredicateBuilder()),
    IS_NOT_NULL("isNotNull",1,new IsNotNullPredicateBuilder()),
    JOIN("join",3,new JoinPredicateBuilder()),
    LIKE("like",2,new LikePredicateBuilder()),
    LT("lt",2,new LtPredicateBuilder()),
    LE("le",2,new LePredicateBuilder()),
    NE("ne",2,new NePredicateBuilder());
    
    private String code;
    private int numArgs;
    private PredicateBuilder pb;
    
    private ConditionFilterType(String code,int numArgs,PredicateBuilder pb){
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
    
    public PredicateBuilder getBuilder(){
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
