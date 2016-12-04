/**
 * DataSourceConfiguration.java 2016年11月15日 10:23
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

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

//    @Bean(name = "readDataSource2")
//    @ConfigurationProperties(prefix = "datasource.read2", locations = "classpath:mybatis/datasources.properties")
//    public DataSource readDataSourceTwo() {
//        log.info("readDataSourceTwo init:{}", dataSourceType);
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }

    public void setDataSourceType(String dataSourceType) {
        log.info("init dataSourceType:{}", dataSourceType);
        try {
            this.dataSourceType = (Class<? extends DataSource>) Class.forName(dataSourceType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
