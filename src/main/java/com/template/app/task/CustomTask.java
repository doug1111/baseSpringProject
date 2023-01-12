package com.template.app.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
@Component
@Slf4j
public class CustomTask {

    /**
     * 每天晚上23点执行一次
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void syncData() {
        log.debug("同步数据");
    }

}
