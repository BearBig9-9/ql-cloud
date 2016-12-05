package com.ql.cloud;

import com.ql.cloud.framework.config.dao.config.datasource.MyAbstractRoutingDataSource;
import com.ql.cloud.framework.config.dao.config.health.RoutingDataSourceHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
    public static void main(String[] args) {
        new SpringApplicationBuilder(SSOServerStart.class).web(true).run(args);
    }

}
