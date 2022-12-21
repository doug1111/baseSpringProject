package com.template.config.message;


import com.template.util.MessageUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 信息事件配置
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
@Component
public class ApplicationEvent implements ApplicationListener<ContextRefreshedEvent> {

	@Resource
	protected MessageSource messageSource;

    /**
     * 处理应用事件
     * @param event 事件
     */
	@Override
	public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
		MessageUtil.setMessageSource(messageSource);
	}

}
