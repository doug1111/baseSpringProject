package com.template.util;


import com.template.app.dto.LoginDTO;

/**
 * 上下文工具类
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
public class ContextUtil {

    private static final ThreadLocal<LoginDTO> myHolder = new ThreadLocal<>();

    public static LoginDTO getContext() {
        return myHolder.get();
    }

    public static void setContext(LoginDTO context) {
        myHolder.set(context);
    }

}