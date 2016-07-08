package com.github.hguerrerojaime.daobot.core.builders.predicate.utils;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;

/**
 * Created by G834244 on 06/02/2016.
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
