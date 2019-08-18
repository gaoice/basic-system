package com.gaoice.system.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 链式事务配置，可以实现跨数据源事务
 */
@Configuration
public class ChainedTransactionManagerConfiguration {

    /**
     * @param jpaTransactionManagerOne     jpa 事务管理器 1
     * @param mybatisTransactionManagerOne mybatis 事务管理器 1
     * @return 链式事务管理器，确保 两个/多个 事务一致性
     */
    @Bean(name = {"jpaOneAndMybatisOneTransactionManager", "jpaTM1AndMybatisTM1"})
    public ChainedTransactionManager jpaOneAndMybatisOneTransactionManager(
            PlatformTransactionManager jpaTransactionManagerOne,
            DataSourceTransactionManager mybatisTransactionManagerOne
    ) {
        return new ChainedTransactionManager(jpaTransactionManagerOne, mybatisTransactionManagerOne);
    }

}
