package com.ql.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
//import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import zipkin.Span;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableZuulProxy
//@EnableZipkinStreamServer
@ComponentScan(basePackages="com.ql.cloud")
public class SSOWebStart {
    private static Logger logger = LoggerFactory.getLogger(SSOWebStart.class);
    public static void main(String[] args) {
        SpringApplication.run(SSOWebStart.class, args);
    }

//    @Bean
//    @ConditionalOnProperty(value = "sso.web.zipkin.enabled", havingValue = "false")
//    public ZipkinSpanReporter spanCollector() {
//        return new ZipkinSpanReporter() {
//            @Override
//            public void report(Span span) {
//                logger.info("ZipKin:::::::::::::{}", span);
//            }
//        };
//    }
}
