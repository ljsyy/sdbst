package com.unifs.sdbst.app.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @version V1.0
 * @title: DataSource
 * @projectName sdbst
 * @description: 数据源配置信息注入实体类
 * @author： 张恭雨
 * @date 2020/4/29 15:53
 */
@RefreshScope //配置文件有更新时动态刷新
@Component
public class DataSourceBean {
    @Value("${spring.datasource.slave0.url}")
    private String primaryUrl;
    @Value("${spring.datasource.slave0.username}")
    private String primaryUsername;
    @Value("${spring.datasource.slave0.password}")
    private String primaryPassword;
    @Value("${spring.datasource.slave0.driver-class-name}")
    private String primayrDriverName;

    @Value("${spring.datasource.slave1.url}")
    private String secondUrl;
    @Value("${spring.datasource.slave1.username}")
    private String secondUsername;
    @Value("${spring.datasource.slave1.password}")
    private String secondPassword;
    @Value("${spring.datasource.slave1.driver-class-name}")
    private String secondDriverName;

    @Value("${spring.datasource.druid.primary.filters}")
    private String filters;
    @Value("${spring.datasource.druid.primary.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.druid.primary.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.druid.primary.maxWait}")
    private long maxWait;
    @Value("${spring.datasource.druid.primary.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.druid.primary.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.druid.primary.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.druid.primary.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.druid.primary.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.druid.primary.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.druid.primary.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.druid.primary.maxOpenPreparedStatements}")
    private int maxOpenPreparedStatements;

    public DataSource getDataSource(String type) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        if ("slave0".equals(type)) {
            druidDataSource.setUrl(primaryUrl);
            druidDataSource.setUsername(primaryUsername);
            druidDataSource.setPassword(primaryPassword);
            druidDataSource.setDriverClassName(primayrDriverName);
        } else {
            druidDataSource.setUrl(secondUrl);
            druidDataSource.setUsername(secondUsername);
            druidDataSource.setPassword(secondPassword);
            druidDataSource.setDriverClassName(secondDriverName);
        }
        druidDataSource.setFilters(filters);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxOpenPreparedStatements);
        return druidDataSource;
    }
}
