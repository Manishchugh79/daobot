package com.github.hguerrerojaime.daobot.core.builders.expression

import spock.lang.Specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Path

import com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper;

/**
 * Created by G834244 on 06/03/2016.
 */
class PathExpressionBuilderSpec extends Specification {

    CriteriaBuilder mockCriteriaBuilder
    Path mockPath

    def setup() {
        mockCriteriaBuilder = Mock()
        mockCriteriaBuilder.literal(!null) >> Mock(Expression)
        mockPath = Mock()
    }

    def "Build"() {

        given: "An arbirtrary path expression builder"

            final String field = "title"

            ExpressionBuilder pathEB = ExpressionBuilderHelper.path(field)

        when: "Executing build"

            pathEB.build(mockCriteriaBuilder,mockPath)

        then: "Path get method executed once"

            1 * mockPath.get(field)

    }

}
