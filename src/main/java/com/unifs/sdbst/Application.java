package com.unifs.sdbst;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/*多数据源 禁用自动配置数据源，DataSourceAutoConfiguration.class, 使用durid
 * 这里一定要排除这里的SpringBootConfiguration，因为我们已经自定义了DataSource，所以需要排序shardingjdbc设置的DataSource
 * ,SpringBootConfiguration.class*/
@SpringBootApplication(exclude = {SpringBootConfiguration.class})
//启用注解缓存机制
@EnableCaching
@ServletComponentScan
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        //设置fastjson autoType=false
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(false);
        SpringApplication.run(Application.class, args);
    }

    /**
     * 　　* @description: 项目打包配置
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/5/8 11:48
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
