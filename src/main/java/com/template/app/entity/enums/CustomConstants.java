package com.template.app.entity.enums;

/**
 * 自定义常数
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
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

		public static final String REGISTER_EMAIL_KEY = "user:register:email:";

		public static final String REGISTER_MOBILE_KEY = "user:register:mobile:";

		public static final String LOGIN_MOBILE_KEY = "user:login:mobile:";
        
        public static final String USER_SHARE_KEY = "user:share:key:";
	}

	/**
	 * 数字相关
	 */
	public static class Number {
		public static final int ZERO = 0;

		public static final int ONE = 1;

		public static final int TWO = 2;

		public static final int THREE = 3;

		public static final int FOUR = 4;

		public static final int FIVE = 5;

		public static final int SIX = 6;

		public static final int SEVEN = 7;
	}

}