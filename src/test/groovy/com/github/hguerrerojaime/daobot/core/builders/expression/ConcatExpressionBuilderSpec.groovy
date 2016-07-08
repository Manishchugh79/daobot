package com.github.hguerrerojaime.daobot.core.builders.expression

import spock.lang.Specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Path

import com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper;

/**
 * Created by G834244 on 06/03/2016.
 */
class ConcatExpressionBuilderSpec extends Specification {

    CriteriaBuilder mockCriteriaBuilder
    Path mockPath

    def setup() {
        mockCriteriaBuilder = Mock()
        mockCriteriaBuilder.literal(!null) >> Mock(Expression)
        mockPath = Mock()
    }

    def "Build"() {

        given: "Three Arbitrary Literal ExpressionBuilders"

            ExpressionBuilder literal1 = ExpressionBuilderHelper.literal("<<")
            ExpressionBuilder literal2 = ExpressionBuilderHelper.literal("0")
            ExpressionBuilder literal3 = ExpressionBuilderHelper.literal(">>")

        and: "A Concat Expression Builder with our 3 literals"

            ExpressionBuilder concatExpressionBuilder = ExpressionBuilderHelper.concat(literal1,literal2,literal3)

        when: "Executing Build"

            concatExpressionBuilder.build(mockCriteriaBuilder,mockPath)

        then: "Concat is being executed 2 times"

            2 * mockCriteriaBuilder.concat(!null,!null) >> Mock(Expression)
    }

    def "GetExpressionBuilders"() {

        given: "Three Arbitrary Literal ExpressionBuilders"

            ExpressionBuilder literal1 = ExpressionBuilderHelper.literal("<<")
            ExpressionBuilder literal2 = ExpressionBuilderHelper.literal("0")
            ExpressionBuilder literal3 = ExpressionBuilderHelper.literal(">>")

        and: "A Concat Expression Builder with our 3 literals"

            ExpressionBuilder concatExpressionBuilder = ExpressionBuilderHelper.concat(literal1,literal2,literal3)

        when: "Getting assigned ExpressionBuilders"

            int actualExpressionBuilders = concatExpressionBuilder.expressionBuilders.length

        then: "Expression Builders should be 3"

            3 == actualExpressionBuilders

    }
}
