package com.github.hguerrerojaime.daobot.core.builders.predicate.utils;

import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;

/**
 * Created by G834244 on 06/02/2016.
 */
public final class PredicateBuilderUtils {

    private PredicateBuilderUtils() {}

    public static boolean allArgsAreExpressionBuilders(Object[] args) {
        return isExpressionBuilder(args[0]) && isExpressionBuilder(args[1]) && isExpressionBuilder(args[2]);
    }

    public static boolean isExpressionBuilder(Object arg) {
        return arg instanceof ExpressionBuilder;
    }

}
