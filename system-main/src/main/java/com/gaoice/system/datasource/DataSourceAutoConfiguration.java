package com.gaoice.system.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 */
@Configuration
public class DataSourceAutoConfiguration {

    /**
     * 数据源一，@Primary 修饰，不指定的情况下默认为此数据源
     * 多数据源的情况下不指定默认数据源，EntityManagerFactoryBuilder 会报错
     *
     * @return dataSourceOne
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源二
     *
     * @return dataSourceTwo
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }

}
