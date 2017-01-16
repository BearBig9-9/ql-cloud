/**
 * GateWayStart.java 2016年11月27日 14:36
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud;

import com.ql.cloud.commons.gateway.filter.LoggerFilter;
import com.ql.cloud.commons.gateway.filter.TestPostFilter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
//import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 *
 * @author 齐龙
 * @date 2016年11月27日 14:36
 */
@SpringCloudApplication
@EnableFeignClients
@EnableHystrixDashboard
@EnableZuulProxy
//@EnableZipkinStreamServer
@ComponentScan(basePackages="com.ql.cloud", lazyInit = true)
public class GateWayStart {
    public static void main(String[] args) {
        new SpringApplicationBuilder(GateWayStart.class).web(true).run(args);
    }

    @Bean
    @ConfigurationProperties(prefix = "filter.logger")
    public LoggerFilter loggerFilter() {
        LoggerFilter loggerFilter = new LoggerFilter();
        return loggerFilter;
    }

    @Bean
    @ConfigurationProperties(prefix = "filter.test")
    public TestPostFilter testPostFilter(){
        return new TestPostFilter();
    }
}
