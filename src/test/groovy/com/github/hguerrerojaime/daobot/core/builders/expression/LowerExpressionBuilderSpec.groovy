package com.github.hguerrerojaime.daobot.core.builders.expression

import spock.lang.Specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Path

import com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper;

/**
 * Created by G834244 on 06/03/2016.
 */
class LowerExpressionBuilderSpec extends Specification {

    CriteriaBuilder mockCriteriaBuilder
    Path mockPath

    def setup() {
        mockCriteriaBuilder = Mock()
        mockCriteriaBuilder.literal(!null) >> Mock(Expression)
        mockPath = Mock()
    }

    def "Build"() {

        given: "An arbirtrary literal expression builder"

            ExpressionBuilder literalEB = ExpressionBuilderHelper.literal("hi")

        and: "A lower case expression builder"

            ExpressionBuilder lowerEB = ExpressionBuilderHelper.lower(literalEB)

        when: "Executing build"

            lowerEB.build(mockCriteriaBuilder,mockPath)

        then: "Criteria builder lower method is executed once"

            1 * mockCriteriaBuilder.lower((Expression) !null)

    }
}
