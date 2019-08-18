package com.gaoice.system.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * mybatis 手动配置
 * 配置以下三项：
 * SqlSessionFactory 依赖 DataSource
 * DataSourceTransactionManager 依赖 DataSource
 * SqlSessionTemplate 依赖 SqlSessionFactory
 */
@Configuration
@MapperScan(basePackages = {"com.gaoice.system.*.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplateOne")
public class MybatisAutoConfigurationOne {

    @Autowired
    @Qualifier("dataSourceTwo")
    private DataSource dataSource;

    /**
     * 配置 sqlSessionFactoryOne
     * 若要配置 sqlSessionFactoryTwo 可以依此类推，进行配置
     * 通过 getResources 指定 mapper.xml 文件位置
     *
     * @return sqlSessionFactoryOne
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryOne() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mapper/mysql/*.xml"));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateOne() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryOne());
    }

    @Bean(name = {"mybatisTransactionManagerOne", "mybatisTM1"})
    public DataSourceTransactionManager mybatisTransactionManagerOne() {
        return new DataSourceTransactionManager(dataSource);
    }

}
