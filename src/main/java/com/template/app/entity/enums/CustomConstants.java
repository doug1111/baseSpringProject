package com.template.app.entity.enums;

/**
 * 自定义常数
 *
 * @author Doug Liu
 * @since 2022-06-14
 */
public class CustomConstants {

    /**
     * 分页参数
     */
    public static class PageQuery {

        public static final int DEFAULT_PAGE = 1;

        public static final int DEFAULT_PAGE_SIZE = 10;

    }

    /**
     * 用户相关
     */
    public static class User {

        public static final String TOKEN_KEY = "user:token:";

        public static final String USER_SHARE_KEY = "user:share:key:";

    }

    /**
     * 数字相关
     */
    public static class Number {

        public static final int ZERO = 0;

        public static final int ONE = 1;

        public static final int TWO = 2;

    }

}