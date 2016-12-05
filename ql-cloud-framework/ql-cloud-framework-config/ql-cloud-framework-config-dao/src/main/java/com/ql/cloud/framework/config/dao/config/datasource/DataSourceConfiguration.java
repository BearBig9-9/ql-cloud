/**
 * DataSourceConfiguration.java 2016年11月15日 10:23
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.datasource;

import com.ql.cloud.commons.base.component.SpringContextHolder;
import com.ql.cloud.framework.config.dao.config.health.RoutingDataSourceHealthIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 *
 * @author 齐龙
 * @date 2016年11月15日 10:23
 *
 */
@Configuration
@ConfigurationProperties(prefix = "datasource.common")
public class DataSourceConfiguration {

    Logger log = LoggerFactory.getLogger(getClass());

    private Integer readSize;

    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "writeDataSource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSource writeDataSource() {
        log.info("writeDataSource init:{}", dataSourceType);
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * 配置读库 有多少个从库就要配置多少个
     * 
     * @return
     */
    @Bean(name = "readDataSource1")
    @ConfigurationProperties(prefix = "datasource.read1")
    public DataSource readDataSourceOne() {
        log.info("readDataSourceOne init:{}", dataSourceType);
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    // @Bean(name = "readDataSource2")
    // @ConfigurationProperties(prefix = "datasource.read2", locations =
    // "classpath:mybatis/datasources.properties")
    // public DataSource readDataSourceTwo() {
    // log.info("readDataSourceTwo init:{}", dataSourceType);
    // return DataSourceBuilder.create().type(dataSourceType).build();
    // }

    @Bean(name = "roundRobinDataSourceProxy")
    public MyAbstractRoutingDataSource roundRobinDataSourceProxy() {
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

    @Autowired
    private HealthAggregator healthAggregator;

    @Bean
    public HealthIndicator dbHealthIndicator(MyAbstractRoutingDataSource roundRobinDataSourceProxy) {
        CompositeHealthIndicator compositeHealthIndicator = new CompositeHealthIndicator(healthAggregator);

        compositeHealthIndicator.addHealthIndicator("roundRobinDataSourceProxy", new RoutingDataSourceHealthIndicator(
                roundRobinDataSourceProxy));
        return compositeHealthIndicator;
    }

    public void setDataSourceType(String dataSourceType) {
        log.info("init dataSourceType:{}", dataSourceType);
        try {
            this.dataSourceType = (Class<? extends DataSource>) Class.forName(dataSourceType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }
}
