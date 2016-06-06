package com.github.hguerrerojaime.daobot.core.builders.expression

import com.github.hguerrerojaime.daobot.core.builders.expression.helpers.ExpressionBuilderHelper
import spock.lang.Specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Path

/**
 * Created by G834244 on 06/03/2016.
 */
class UpperExpressionBuilderSpec extends Specification {

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

        and: "A upper case expression builder"

            ExpressionBuilder upperEB = ExpressionBuilderHelper.upper(literalEB)

        when: "Executing build"

            upperEB.build(mockCriteriaBuilder,mockPath)

        then: "Criteria builder lower method is executed once"

            1 * mockCriteriaBuilder.upper((Expression) !null)

    }
}
