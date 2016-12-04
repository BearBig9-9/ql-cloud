package com.ql.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaStart {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaStart.class).web(true).run(args);
    }
}
