package com.github.hguerrerojaime.daobot.core.builders.expression.helpers;

import com.github.hguerrerojaime.daobot.core.builders.expression.*;

/**
 * Created by G834244 on 06/02/2016.
 */
public final class ExpressionBuilderHelper {

    private ExpressionBuilderHelper() {}

    public static <T> LiteralExpressionBuilder<T> literal(T value) {
        return new LiteralExpressionBuilder<T>(value);
    }

    public static PathExpressionBuilder path(String fieldPath) {
        return new PathExpressionBuilder(fieldPath);
    }

    public static UpperExpressionBuilder upper(ExpressionBuilder expressionBuilder) {
        return new UpperExpressionBuilder(expressionBuilder);
    }

    public static LowerExpressionBuilder lower(ExpressionBuilder expressionBuilder) {
        return new LowerExpressionBuilder(expressionBuilder);
    }

    public static ConcatExpressionBuilder concat(ExpressionBuilder... expressionBuilders) {
        return new ConcatExpressionBuilder(expressionBuilders);
    }

}
