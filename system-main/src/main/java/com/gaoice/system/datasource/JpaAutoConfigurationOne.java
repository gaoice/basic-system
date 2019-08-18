package com.gaoice.system.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * jpa 配置依赖以下三项：
 * LocalContainerEntityManagerFactoryBean
 * EntityManager
 * PlatformTransactionManager
 */
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryOne",
        transactionManagerRef = "jpaTransactionManagerOne",
        basePackages = {"com.gaoice.system.*.repository"})
public class JpaAutoConfigurationOne {

    @Autowired
    @Qualifier("dataSourceOne")
    private DataSource dataSource;
    /**
     * jpaProperties 和 hibernateProperties 两个配置文件对应 spring.jpa 前缀的配置
     * 如果多个 jpa 并且每个 jpa 配置不同，可以指定不同的前缀
     */
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryOne(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("com.gaoice.system.*.entity")
                .persistenceUnit("persistenceUnitOne")
                .build();
    }

    @Bean
    public EntityManager entityManagerOne(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryOne(builder).getObject().createEntityManager();
    }

    @Bean(name = {"jpaTransactionManagerOne", "jpaTM1"})
    public PlatformTransactionManager jpaTransactionManagerOne(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryOne(builder).getObject());
    }

}
