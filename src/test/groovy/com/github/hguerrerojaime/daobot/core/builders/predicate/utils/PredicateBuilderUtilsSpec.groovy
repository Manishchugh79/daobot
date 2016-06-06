package com.github.hguerrerojaime.daobot.core.builders.predicate.utils

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder
import spock.lang.Specification

/**
 * Created by G834244 on 06/06/2016.
 */
class PredicateBuilderUtilsSpec extends Specification {



    def "test AllArgsAreExpressionBuilders expecting true"() {

        when: "Executing method"

            boolean result = PredicateBuilderUtils.allArgsAreExpressionBuilders(Mock(ExpressionBuilder),Mock(ExpressionBuilder))

        then: "Result should be true"

            result == true

    }

    def "test AllArgsAreExpressionBuilders expecting false"() {

        when: "Executing method"

        boolean result = PredicateBuilderUtils.allArgsAreExpressionBuilders("Not an Expression Builder",Mock(ExpressionBuilder))

        then: "Result should be false"

        result == false

    }

}
