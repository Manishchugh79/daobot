package com.github.hguerrerojaime.daobot.core.builders.predicate

import com.github.hguerrerojaime.daobot.core.ConditionFilter
import com.github.hguerrerojaime.daobot.core.ConditionFilterType
import com.github.hguerrerojaime.daobot.integration.eo.BookEO
import spock.lang.Specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path

import static com.github.hguerrerojaime.daobot.core.builders.expression.helpers.ExpressionBuilderHelper.path

@SuppressWarnings("GroovyAssignabilityCheck")
class BetweenPredicateBuilderSpec extends Specification {

    Path mockPath
    CriteriaBuilder criteriaBuilder;


    def setup() {

        mockPath = Mock()
        criteriaBuilder = Mock()


    }


    def "test Build with all expression arguments"() {

        given: "A Between PredicateBuilder instance"

            PredicateBuilder predicateBuilder = new BetweenPredicateBuilder()

        and: "A condition filter with 3 expression arguments"

            ConditionFilter conditionFilter =
                 new ConditionFilter(
                         ConditionFilterType.BETWEEN,
                         path("releaseDate"),
                         path("releaseDate"),
                         path("releaseDate")
                 )

        when: "Executing build predicate"

            predicateBuilder.build(conditionFilter,criteriaBuilder,mockPath,BookEO)

        then: "criteriaBuilder.between has executed once"

            1 * criteriaBuilder.between(_,_ ,_)

    }

    def "test Build with path expression argument and parameter values"() {

        given: "A Between PredicateBuilder instance"

            PredicateBuilder predicateBuilder = new BetweenPredicateBuilder()

        and: "A condition filter with 3 expression arguments"

            ConditionFilter conditionFilter =
                    new ConditionFilter(
                            ConditionFilterType.BETWEEN,
                            path("releaseDate"),
                            new Date(),
                            new Date()
                    )

        when: "Executing build predicate"

            predicateBuilder.build(conditionFilter,criteriaBuilder,mockPath,BookEO)

        then: "criteriaBuilder.between has executed once"

            1 * criteriaBuilder.between(_,_ ,_)

    }

}
