package com.ql.cloud;

import com.ql.cloud.framework.config.dao.config.datasource.MyAbstractRoutingDataSource;
import com.ql.cloud.framework.config.dao.config.health.RoutingDataSourceHealthIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import zipkin.Span;

/**
 * 开始方法
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
// @EnableCircuitBreaker
@EnableZuulProxy
@ComponentScan(lazyInit = true)
// basePackages="com.ql.cloud",
public class SSOServerStart {
    private static Logger logger = LoggerFactory.getLogger(SSOServerStart.class);
    public static void main(String[] args) {
        new SpringApplicationBuilder(SSOServerStart.class).web(true).run(args);
    }

//    @Bean
//    @ConditionalOnProperty(value = "sso.server.zipkin.enabled", havingValue = "false")
//    public ZipkinSpanReporter spanCollector() {
//        return new ZipkinSpanReporter() {
//            @Override
//            public void report(Span span) {
//                logger.info("ZipKin:::::::::::::{}", span);
//            }
//        };
//    }
}
