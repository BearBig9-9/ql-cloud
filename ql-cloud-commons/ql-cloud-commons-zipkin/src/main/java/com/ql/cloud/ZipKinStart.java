package com.ql.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipKinStart {
    public static void main(String[] args) {
        // 教程链接 https://yq.aliyun.com/articles/60165
        SpringApplication.run(ZipKinStart.class, args);
    }
}
