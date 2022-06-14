package com.template.util;


import java.util.Collection;

import com.template.common.BizException;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.stereotype.Component;


/**
 * 业务异常抛出校验
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
@Component
public class BusinessCheck {

    public static void trueThrow(boolean expression, Integer code, String message) {
        if (expression) {
            throw new BizException(code, message);
        }
    }

    public static void falseThrow(boolean expression, Integer code) {
        trueThrow(!expression, code);
    }

    public static void trueThrow(boolean expression, Integer code) {
        trueThrow(expression, code, MessageUtil.get(String.valueOf(code)));
    }

    public static void blankThrow(String expression, Integer code) {
        trueThrow(expression == null || expression.isEmpty(), code,
                MessageUtil.get(String.valueOf(code)));
    }

    public static <E> void blankThrow(Collection<E> expression, Integer code) {
        trueThrow(CollectionUtils.isEmpty(expression), code, MessageUtil.get(String.valueOf(code)));
    }

    public static void nullThrow(Object expression, Integer code) {
        trueThrow(expression == null, code, MessageUtil.get(String.valueOf(code)));
    }

    public static void notNullThrow(Object expression, Integer code) {
        trueThrow(expression != null, code, MessageUtil.get(String.valueOf(code)));
    }

}
