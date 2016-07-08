package com.github.hguerrerojaime.daobot.core.builders.predicate

import com.github.hguerrerojaime.daobot.core.ConditionFilter
import com.github.hguerrerojaime.daobot.core.ConditionFilterType
import com.github.hguerrerojaime.daobot.integration.eo.BookEO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

import spock.lang.Specification

import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.path;

class LikePredicateBuilderSpec extends Specification {
	
	Path mockPath
	CriteriaBuilder criteriaBuilder;


	def setup() {

		mockPath = Mock()
		criteriaBuilder = Mock()

	}


	def "test Build with all expression arguments"() {

		given: "An Equal PredicateBuilder instance"

			PredicateBuilder predicateBuilder = new LikePredicateBuilder()

		and: "A condition filter with 3 expression arguments"

			ConditionFilter conditionFilter =
					new ConditionFilter(
							ConditionFilterType.LIKE,
							path("title"),
							path("title")
					)

		when: "Executing build predicate"

			predicateBuilder.build(conditionFilter,criteriaBuilder,mockPath,BookEO)

		then: "criteriaBuilder.like has executed once"

			1 * criteriaBuilder.like(_,_)

	}

	def "test Build with path expression argument and parameter values"() {

		given: "An Equal PredicateBuilder instance"

			PredicateBuilder predicateBuilder = new LikePredicateBuilder()

		and: "A condition filter with 3 expression arguments"

			ConditionFilter conditionFilter =
					new ConditionFilter(
							ConditionFilterType.EQ,
							path("title"),
							"value"
					)

		when: "Executing build predicate"

			predicateBuilder.build(conditionFilter,criteriaBuilder,mockPath,BookEO)

		then: "criteriaBuilder.equal has executed once"

			1 * criteriaBuilder.like(_,_)

	}

}
