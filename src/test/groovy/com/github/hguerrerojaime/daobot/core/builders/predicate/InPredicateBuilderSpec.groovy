package com.github.hguerrerojaime.daobot.core.builders.predicate

import spock.lang.Specification

import com.github.hguerrerojaime.daobot.core.ConditionFilter
import com.github.hguerrerojaime.daobot.core.ConditionFilterType
import com.github.hguerrerojaime.daobot.integration.eo.BookEO

import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.path;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

class InPredicateBuilderSpec extends Specification {

	Path mockPath
	CriteriaBuilder criteriaBuilder;


	def setup() {

		mockPath = Mock()
		criteriaBuilder = Mock()

	}


	def "test Build with all expression arguments"() {

		given: "An Equal PredicateBuilder instance"

			PredicateBuilder predicateBuilder = new InPredicateBuilder()
		
		and: "Some collection"
		
			final someCollection = [1L,2L]

		and: "A condition filter with 3 expression arguments"

			ConditionFilter conditionFilter =
					new ConditionFilter(
							ConditionFilterType.IN,
							path("id"),
							someCollection
					)
				
		
		and: "Mock Id Path"
			Path mockIdPath = Mock(Path)
			
		when: "Executing build predicate"
			predicateBuilder.build(conditionFilter,criteriaBuilder,mockPath,BookEO)

		then: "in has executed once"
			1 * mockPath.get("id") >> mockIdPath
			1 * mockIdPath.in(someCollection)

	}

	def "test Build with path expression argument and parameter values"() {

		given: "An Equal PredicateBuilder instance"

			PredicateBuilder predicateBuilder = new InPredicateBuilder()

		and: "A condition filter with 3 expression arguments"

			ConditionFilter conditionFilter =
					new ConditionFilter(
							ConditionFilterType.IN,
							path("id"),
							path("id")
					)
		and: "Mock Id Path"
		
			Path mockIdPath = Mock(Path)

		when: "Executing build predicate"

			predicateBuilder.build(conditionFilter,criteriaBuilder,mockPath,BookEO)

		then: "in has executed once"
			2 * mockPath.get("id") >> mockIdPath
			1 * mockIdPath.in(!null)

	}

}
