package com.template.app.service;

/**
 * 异步服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
public interface IAsyncService {

	/**
	 * 分配用户广告
	 * @param userId 用户ID
	 */
	void createRecommendAd(Long userId);

}
