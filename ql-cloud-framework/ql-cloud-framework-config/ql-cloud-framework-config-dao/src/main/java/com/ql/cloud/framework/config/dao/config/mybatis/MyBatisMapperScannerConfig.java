/**
 * MyBatisMapperScannerConfig.java 2016年11月15日 13:05
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.mybatis;

import com.ql.cloud.framework.config.dao.config.mybatis.MybatisConfiguration;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描包配置
 *
 * @author 齐龙
 * @date 2016年11月15日 13:05
 *
 */
@Configuration
@AutoConfigureAfter(MybatisConfiguration.class)
public class MyBatisMapperScannerConfig {
    Logger log = LoggerFactory.getLogger(getClass());
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        log.info("------------init MapperScannerConfigurer-------------");
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.ql.cloud.**.dao");
        return mapperScannerConfigurer;
    }




}
