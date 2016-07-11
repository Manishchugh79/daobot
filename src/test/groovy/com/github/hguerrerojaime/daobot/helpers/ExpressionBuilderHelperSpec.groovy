package com.github.hguerrerojaime.daobot.helpers

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder

import spock.lang.Specification

/**
 * Created by G834244 on 06/03/2016.
 */
class ExpressionBuilderHelperSpec extends Specification {

    def "Literal"() {

        given: "Null ExpressionBuilder Variable"

            ExpressionBuilder expressionBuilder;

        and: "Arbitrary Literal Value"

            String someLiteral = "Some Literal";

        when: "Executing Literal"

            expressionBuilder = ExpressionBuilderHelper.literal(someLiteral);

        then: "The ExpressionBuilder Variable is not null"

            expressionBuilder != null

        and: "The literal is the same as the assigned"

            expressionBuilder.literal == someLiteral


    }

    def "Path"() {

        given: "Null ExpressionBuilder Variable"

            ExpressionBuilder expressionBuilder;

        and: "Arbitrary Path Value"

            String fieldPath = "title";

        when: "Executing Path"

            expressionBuilder = ExpressionBuilderHelper.path(fieldPath);

        then: "The ExpressionBuilder Variable is not null"

            expressionBuilder != null

        and: "The path is the same as the assigned"

            expressionBuilder.fieldPath == fieldPath

    }

    def "Upper"() {

        given: "Null ExpressionBuilder Variable"

            ExpressionBuilder expressionBuilder;

        and: "An arbitrary path expression builder"

        String fieldPath = "title";

            ExpressionBuilder pathExpressionBuilder = ExpressionBuilderHelper.path(fieldPath)

        when: "Executing Upper"

            expressionBuilder = ExpressionBuilderHelper.upper(pathExpressionBuilder);

        then: "The ExpressionBuilder Variable is not null"

            expressionBuilder != null

        and: "The Assigned ExpressionBuilder should be the given PathExpressionBuilder"

            expressionBuilder.expressionBuilder == pathExpressionBuilder

    }

    def "Lower"() {

        given: "Null ExpressionBuilder Variable"

            ExpressionBuilder expressionBuilder;

        and: "An arbitrary path expression builder"

            String fieldPath = "title";

        ExpressionBuilder pathExpressionBuilder = ExpressionBuilderHelper.path(fieldPath)

        when: "Executing Lower"

            expressionBuilder = ExpressionBuilderHelper.lower(pathExpressionBuilder);

        then: "The ExpressionBuilder Variable is not null"

            expressionBuilder != null

        and: "The Assigned ExpressionBuilder should be the given PathExpressionBuilder"

            expressionBuilder.expressionBuilder == pathExpressionBuilder

    }

    def "Concat"() {

        given: "Null ExpressionBuilder Variable"

            ExpressionBuilder expressionBuilder;

        and: "A Path ExpressionBuilder"

            ExpressionBuilder pathExpressionBuilder = ExpressionBuilderHelper.path("title")

        and: "A Literal ExpressionBuilder"

            ExpressionBuilder literalExpressionBuilder = ExpressionBuilderHelper.literal("->")

        when: "Executing Concat"

            expressionBuilder = ExpressionBuilderHelper.concat(literalExpressionBuilder,pathExpressionBuilder)

        then: "The ExpressionBuilder variable is not null"

            expressionBuilder != null

        and: "The expression builders assigned should be the same as the arguments passed [2]"

            expressionBuilder.expressionBuilders.length == 2

    }
}
