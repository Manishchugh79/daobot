package com.github.hguerrerojaime.daobot.core


import spock.lang.Specification;

class ConditionFilterTypeSpec extends Specification {

	def "validate enum properties"() {
		
		given: "loaded enum by code"
		ConditionFilterType cond = ConditionFilterType.byCode("eq")
		
		expect: "enum properties are not null"
		cond.builder != null
		cond.code != null
		cond.numArgs != null
	}
	
	def "validate enum is null when code is invalid"() {
		
		given: "loaded enum by code"
		ConditionFilterType cond = ConditionFilterType.byCode("invalid")
		
		expect: "enum is null"
		cond == null
	}
	

}
