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
	 * Feed流广告相关
	 */
	public static class FeedAds {
		public static final String FEED_ADS_KEY = "feed:ads:key:";
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

	/**
	 * 第三房token相关
	 */
	public static class ThirdToken {
		public static final String FEED_ADS_KEY = "third:token:";
	}

	public static class NoticeTemplate{
		public static final String CONTENT_MENTION = "你在帖子中被提及！";
		public static final String CONTENT_REPLAY_MENTION = "你在帖子回复中被提及！";
		public static final String CONTENT_REPRINT_MENTION = "你的帖子被转载！";
		public static final String PRIVATE_MESSAGE = "你有新的私信消息！";
		public static final String CONTENT_LIKE_MENTION = "你的帖子被点赞了！";
		public static final String CONTENT_FAVOURITE_MENTION = "你的帖子被收藏了！";
	}
}