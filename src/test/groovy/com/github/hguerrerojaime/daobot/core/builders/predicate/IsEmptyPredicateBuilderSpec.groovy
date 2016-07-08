package com.github.hguerrerojaime.daobot.core.builders.predicate

import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.path;

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path

import com.github.hguerrerojaime.daobot.core.ConditionFilter
import com.github.hguerrerojaime.daobot.core.ConditionFilterType
import com.github.hguerrerojaime.daobot.integration.eo.BookEO;

import spock.lang.Specification

class IsEmptyPredicateBuilderSpec extends Specification {

	Path mockPath
	CriteriaBuilder criteriaBuilder;


	def setup() {
		mockPath = Mock()
		criteriaBuilder = Mock()
	}
	
	def "test is empty predicate builder"() {
		
		given: "A PredicateBuilder instance"
			PredicateBuilder predicateBuilder = new IsEmptyPredicateBuilder()
		
		and: "A condition filter"
			ConditionFilter conditionFilter =
			new ConditionFilter(
					ConditionFilterType.IS_EMPTY,
					path("title")
			)
		when: "Executing is empty"
			predicateBuilder.build(conditionFilter, criteriaBuilder, mockPath, BookEO)
		then: "method executed once"
			1 * mockPath.get("title") >> Mock(Path)
			1 * criteriaBuilder.isEmpty(!null)
		
	}

}
