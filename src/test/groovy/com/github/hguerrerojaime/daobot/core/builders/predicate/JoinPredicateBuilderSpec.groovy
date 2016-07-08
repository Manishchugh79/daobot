package com.github.hguerrerojaime.daobot.core.builders.predicate

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.From
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType

import spock.lang.Specification

import com.github.hguerrerojaime.daobot.core.CB
import com.github.hguerrerojaime.daobot.core.ConditionFilter
import com.github.hguerrerojaime.daobot.core.ConditionFilterType
import com.github.hguerrerojaime.daobot.integration.eo.BookEO

class JoinPredicateBuilderSpec extends Specification {
	
	From mockPath
	CriteriaBuilder criteriaBuilder;


	def setup() {
		mockPath = Mock()
		criteriaBuilder = Mock()
	}
	
	def "test predicate builder"() {
		
		given: "A PredicateBuilder instance"
			PredicateBuilder predicateBuilder = new JoinPredicateBuilder()
		
		and: "A condition filter"
			ConditionFilter conditionFilter =
			new ConditionFilter(
					ConditionFilterType.JOIN,
					"title",
					new CB(),
					JoinType.INNER
			)
		when: "Executing is empty"
			predicateBuilder.build(conditionFilter, criteriaBuilder, mockPath, BookEO)
		then: "method executed once"
			notThrown Exception
		
	}
	
	
	def "test predicate builder with invalid field"() {
		
		given: "A PredicateBuilder instance"
			PredicateBuilder predicateBuilder = new JoinPredicateBuilder()
		
		and: "A condition filter"
			ConditionFilter conditionFilter =
			new ConditionFilter(
					ConditionFilterType.JOIN,
					"invalidField",
					new CB(),
					JoinType.INNER
			)
		when: "Executing is empty"
			predicateBuilder.build(conditionFilter, criteriaBuilder, mockPath, BookEO)
		then: "method executed once"
			thrown IllegalArgumentException
		
	}

}
