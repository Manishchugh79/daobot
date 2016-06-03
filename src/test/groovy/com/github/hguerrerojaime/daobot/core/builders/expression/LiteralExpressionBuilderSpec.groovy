package com.github.hguerrerojaime.daobot.core.builders.expression

import com.github.hguerrerojaime.daobot.core.builders.expression.helpers.ExpressionBuilderHelper
import spock.lang.Specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path

/**
 * Created by G834244 on 06/03/2016.
 */
class LiteralExpressionBuilderSpec extends Specification {

    CriteriaBuilder mockCriteriaBuilder
    Path mockPath

    def setup() {
        mockCriteriaBuilder = Mock()
        mockPath = Mock()
    }

    def "Build"() {
        given: "Three Arbitrary Literal ExpressionBuilders"

            ExpressionBuilder literal = ExpressionBuilderHelper.literal("0")

        when: "Executing Build"

            literal.build(mockCriteriaBuilder,mockPath)

        then: "Literal is executed 1 time"

            1 * mockCriteriaBuilder.literal(!null)
    }
}
