package com.template.util;


import com.template.app.dto.LoginDto;

/**
 * 上下文工具类
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
public class ContextUtil {

    private static final ThreadLocal<LoginDto> myHolder = new ThreadLocal<>();

    public static void setContext(LoginDto context) {
        myHolder.set(context);
    }

    public static LoginDto getContext() {
        return myHolder.get();
    }

}