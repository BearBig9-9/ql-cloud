/**
 * MybatisConfiguration.java 2016年11月15日 11:19
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.mybatis;

import com.ql.cloud.commons.base.component.SpringContextHolder;
import com.ql.cloud.framework.config.dao.config.datasource.DataSourceConfiguration;
import com.ql.cloud.framework.config.dao.config.datasource.DataSourceType;
import com.ql.cloud.framework.config.dao.config.datasource.MyAbstractRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * MyBatis配置
 *
 * @author 齐龙
 * @date 2016年11月15日 11:19
 *
 */
@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
@ConfigurationProperties(prefix = "datasource.common", locations = "classpath:mybatis/datasources.properties")
public class MybatisConfiguration extends MybatisAutoConfiguration {

    private Logger log = LoggerFactory.getLogger(getClass());

    private Integer readSize;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactories() throws Exception {
        log.info("sqlSessionFactory init");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(roundRobinDataSourceProxy());

        // 分页插件
        /*
         * PageHelper pageHelper = new PageHelper(); Properties properties = new Properties();
         * properties.setProperty("reasonable", "true");
         * properties.setProperty("supportMethodsArguments", "true");
         * properties.setProperty("returnPageInfo", "check"); properties.setProperty("params",
         * "count=countSql"); pageHelper.setProperties(properties);
         * 
         * //添加插件 bean.setPlugins(new Interceptor[]{pageHelper});
         */

        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setConfigLocation(resolver.getResource("classpath:/mybatis/mybatis-config.xml"));
            bean.setMapperLocations(resolver.getResources("classpath:/mybatis/sqlmapper/**/*.xml"));
        } catch (Exception e) {
            log.error("未找到MyBatis SQLMapper文件", e);
        }
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 有多少个数据源就要配置多少个bean
     * 
     * @return
     */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSourceProxy() {
        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(readSize);
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");
        // 写
        targetDataSources.put(DataSourceType.write.getType(), SpringContextHolder.getBean("writeDataSource"));

        for (int i = 0; i < readSize; i++) {
            targetDataSources.put(i, SpringContextHolder.getBean("readDataSource" + (i + 1)));
        }
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }
}
