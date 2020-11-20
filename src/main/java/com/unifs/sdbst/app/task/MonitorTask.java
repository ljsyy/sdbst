package com.unifs.sdbst.app.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @version V1.0
 * @title MonitorTask
 * @projectName sdbst
 * @description 接口定时监测任务:1、从数据库中获取监控接口的请求路径
 *                            2、请求接口路径，获取响应接口
 *                            3、分析相应接口，判断是否正常，如果不正常则将对应数据库中接口路径替换为临时页面，如果正常则下一个
 * @author lx
 * @date 2020/11/20
 */
@Configuration
@EnableScheduling
public class MonitorTask {
    @Value("${monitor.base.url}")
    private String monitorConfigPath;

    @Scheduled(cron="0 0 0/1 * * ?")
    public void monitorAllTask(){

    }
}
