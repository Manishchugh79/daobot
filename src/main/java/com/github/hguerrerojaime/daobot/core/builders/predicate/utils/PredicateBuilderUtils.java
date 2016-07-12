package com.github.hguerrerojaime.daobot.core.builders.predicate.utils;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;

/**
 * Predicate Builder utility methods
 * 
 * @author Humberto Guerrero Jaime
 */
public final class PredicateBuilderUtils {

    public static boolean allArgsAreExpressionBuilders(Object[] args) {

        boolean result = true;

        for (Object arg : args) {

            if (!isExpressionBuilder(arg)) {
                result = false;
                break;
            }

        }

        return result;
    }

    public static boolean isExpressionBuilder(Object arg) {
        return arg instanceof ExpressionBuilder;
    }

}
