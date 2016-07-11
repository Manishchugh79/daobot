package com.github.hguerrerojaime.daobot.helpers;

import com.github.hguerrerojaime.daobot.core.builders.expression.ConcatExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.expression.ExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.expression.LiteralExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.expression.LowerExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.expression.PathExpressionBuilder;
import com.github.hguerrerojaime.daobot.core.builders.expression.UpperExpressionBuilder;

/**
 * Created by Humberto Guerrero Jaime on 06/02/2016.
 */
@SuppressWarnings("rawtypes")
public final class ExpressionBuilderHelper {

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
