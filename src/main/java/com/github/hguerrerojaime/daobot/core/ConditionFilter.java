package com.github.hguerrerojaime.daobot.core;

/**
 * @author Humberto Guerrero Jaime
 *
 */
public class ConditionFilter implements QueryFilter {

	private Type conditionType;
	private Object[] args;
	
	public ConditionFilter(Type conditionType,Object args[]) {
		super();
		this.conditionType = conditionType;
		this.args = args;
	}

	public Type getConditionType() {
		return conditionType;
	}


	public Object[] getArgs() {
		return args;
	}
	

	static enum Type{
		BETWEEN("between",3),
		EQ("eq",2),
		EQ_PROPERTY("eqProperty",2),
		GT("gt",2),
		GT_PROPERTY("gtProperty",2),
		GE("ge",2),
		GE_PROPERTY("geProperty",2),
		ID_EQ("idEq",1),
		ID_NE("idNe",1),
		ILIKE("ilike",2),
		IN("in",2),
		IS_EMPTY("isEmpty",1),
		IS_NOT_EMPTY("isNotEmpty",1),
		IS_NULL("isNull",1),
		IS_NOT_NULL("isNotNull",1),
		JOIN("join",3),
		LIKE("like",2),
		LT("lt",2),
		LT_PROPERTY("ltProperty",2),
		LE("le",2),
		LE_PROPERTY("leProperty",2),
		NE("ne",2),
		NE_PROPERTY("neProperty",2);
		
		private String code;
		private int numArgs;
		
		private Type(String code,int numArgs){
			this.code = code;
			this.numArgs = numArgs;
		}
		
		public String getCode(){
			return code;
		}
		
		public int getNumArgs(){
		    return numArgs;
		}
		
		public static Type byCode(String code){
			
			Type result = null;
			
			for(Type type : values()){
				
				if(type.getCode().equals(code)){
					result = type;
					break;
				}
				
			}
			
			return result;
			
		}
	}
}
