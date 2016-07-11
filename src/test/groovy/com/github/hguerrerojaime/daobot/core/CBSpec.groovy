package com.github.hguerrerojaime.daobot.core

import com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper;

import spock.lang.Specification;

class CBSpec extends Specification {

	
	def "validate 'and' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.and(new CB())
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'or' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.or(new CB())
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'not' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.not(new CB())
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'eq' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.eq("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'eqProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.eqProperty("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'ne' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.ne("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'neProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.neProperty("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'gt' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.gt("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'gtProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.gtProperty("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'ge' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.ge("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'geProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.geProperty("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'lt' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.lt("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'ltProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.ltProperty("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'le' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.le("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'leProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.leProperty("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'isNull' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.isNull("field")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'isNotNull' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.isNotNull("field")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'like' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.like("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'like' with expressions added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.like(ExpressionBuilderHelper.path("field"),ExpressionBuilderHelper.path("field"))
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'likeProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.likeProperty("field","field")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'ilike' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.ilike("field","value")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	
	
	def "validate 'ilike' with expressions added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.ilike(ExpressionBuilderHelper.path("field"),ExpressionBuilderHelper.path("field"))
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'ilikeProperty' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.ilikeProperty("field","field")
		
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	
	def "validate 'in' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.in("field",["value"])
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'in' with expressions added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.in(ExpressionBuilderHelper.path("field"),ExpressionBuilderHelper.path("field"))
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'between' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.between("field","value","value")
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'between' with expressions added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.between(ExpressionBuilderHelper.path("field"),ExpressionBuilderHelper.path("field"),ExpressionBuilderHelper.path("field"))
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'join' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.join("field",new CB())
		then: "criteria should have 1 element"
		
		criteria.filters.size() == 1
		
	}
	
	def "validate 'sort' added to the filters"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.sort("field")
		then: "criteria should have 1 sort element"
		
		criteria.sortList.size() == 1
		
	}
	
	def "validate 'getBuild'"() {
		
		given: "a criteria builder"
		
		def criteria = new CB()
		
		when: "executing"
		
		criteria.build()

		then: "criteria's build should not be null"
		
		criteria.build != null
		
	}

}
