package com.unifs.sdbst.app.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @创建人 张恭雨
 * @创建时间 2018/8/29
 * @描述：多数据源配置，第二数据源
 */
@Configuration
@MapperScan(basePackages = "com.unifs.sdbst.app.dao.second", sqlSessionTemplateRef = "secondSqlSessionTemplate")
public class DataSourceSecondConfig {

    @Autowired
    private DataSourceBean bean;

    //    第二数据源 @Qualifier("dataSource") DataSource dataSource
    @RefreshScope //配置文件有更新时动态刷新
    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.second")
    public DataSource secondDataSource() throws SQLException {
        //使用durid数据源
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
//        return getShardingDataSource();

    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置bean基础路径
        bean.setTypeAliasesPackage("com.unifs.sdbst.app.bean.*");
        /*加载所有的mapper.xml映射文件*/
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/app/second/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager secondTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //配置sharding数据源
    private DataSource getShardingDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 配置第一个数据源
        dataSourceMap.put("ds0", bean.getDataSource("slave0"));
        // 配置第二个数据源
        dataSourceMap.put("ds1", bean.getDataSource("slave1"));

        // 配置sys_log表规则
        TableRuleConfiguration sysLogTableRuleConfig = new TableRuleConfiguration("sys_log","ds${0..1}.sys_log_${0..1}");        // 配置分库 + 分表策略
        sysLogTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("SUB_LIBRARY", "ds${SUB_LIBRARY % 2}"));
        sysLogTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("SUB_TABLE", "sys_log_${SUB_TABLE % 2}"));

        // 配置sdbst_browse表规则
        TableRuleConfiguration browseTableRuleConfig = new TableRuleConfiguration("sdbst_browse","ds${0..1}.sdbst_browse_${0..1}");        // 配置分库 + 分表策略
        browseTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("SUB_LIBRARY", "ds${SUB_LIBRARY % 2}"));
        browseTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("SUB_TABLE", "sdbst_browse_${SUB_TABLE % 2}"));

        // 配置sys_fingertips_log表规则
        TableRuleConfiguration fingertipsLogTableRuleConfig = new TableRuleConfiguration("sys_fingertips_log","ds0.sys_fingertips_log");        // 配置分库 + 分表策略

        // 配置sys_hard_log表规则
        TableRuleConfiguration hardLogTableRuleConfig = new TableRuleConfiguration("sys_hard_log","ds0.sys_hard_log");        // 配置分库 + 分表策略

        // 配置server_info表规则
        TableRuleConfiguration serverInfoTableRuleConfig = new TableRuleConfiguration("server_info","ds0.server_info");        // 配置分库 + 分表策略

        // 配置error_reporting_statistics表规则
        TableRuleConfiguration errorTableRuleConfig = new TableRuleConfiguration("error_reporting_statistics","ds0.error_reporting_statistics");        // 配置分库 + 分表策略

        // 配置sdbst_office表规则
        TableRuleConfiguration sdbstOfficeTableRuleConfig = new TableRuleConfiguration("sdbst_office","ds0.sdbst_office");        // 配置分库 + 分表策略

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(sysLogTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(browseTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(fingertipsLogTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(hardLogTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(serverInfoTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(errorTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(sdbstOfficeTableRuleConfig);
        // 获取数据源对象
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
    }



}
