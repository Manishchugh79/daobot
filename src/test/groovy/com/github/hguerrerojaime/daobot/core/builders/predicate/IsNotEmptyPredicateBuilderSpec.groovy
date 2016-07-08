package com.github.hguerrerojaime.daobot.core.builders.predicate

import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.path;

import com.github.hguerrerojaime.daobot.core.ConditionFilter
import com.github.hguerrerojaime.daobot.core.ConditionFilterType
import com.github.hguerrerojaime.daobot.integration.eo.BookEO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

import spock.lang.Specification

class IsNotEmptyPredicateBuilderSpec extends Specification {

	Path mockPath
	CriteriaBuilder criteriaBuilder;


	def setup() {
		mockPath = Mock()
		criteriaBuilder = Mock()
	}
	
	def "test is not empty predicate builder"() {
		
		given: "A PredicateBuilder instance"
			PredicateBuilder predicateBuilder = new IsNotEmptyPredicateBuilder()
		
		and: "A condition filter"
			ConditionFilter conditionFilter =
			new ConditionFilter(
					ConditionFilterType.IS_NOT_EMPTY,
					path("title")
			)
		when: "Executing is empty"
			predicateBuilder.build(conditionFilter, criteriaBuilder, mockPath, BookEO)
		then: "method executed once"
			1 * mockPath.get("title") >> Mock(Path)
			1 * criteriaBuilder.isNotEmpty(!null)
		
	}

}
