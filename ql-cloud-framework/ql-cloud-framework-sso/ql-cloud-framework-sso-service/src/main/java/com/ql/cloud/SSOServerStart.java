package com.ql.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * 开始方法
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableZuulProxy
@ComponentScan(basePackages="com.ql.cloud", lazyInit = true)
public class SSOServerStart {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SSOServerStart.class).web(true).run(args);
    }
}
