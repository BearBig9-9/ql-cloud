/**
 * DataSourceAOP.java 2016年11月15日 11:36
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * 事务切点
 *
 * @author 齐龙
 * @date 2016年11月15日 11:36
 */
@Aspect
@Order(-10000)
@Component
public class DataSourceAOP {

    private Logger log = LoggerFactory.getLogger(getClass());

    // @Before("execution(* com.ql.cloud..service..*.find*(..)) or execution(* com.ql.cloud..service..*.get*(..))")
    // public void setReadDataSourceType() {
    // DataSourceContextHolder.read();
    // log.debug("dataSource切换到：Read");
    // }

    @Before("execution(* com.ql.cloud..service..*.*(..))")
    public void setWriteDataSourceType(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Transactional transactional = method.getAnnotation(Transactional.class);
        if (transactional != null && !transactional.readOnly()) {
            DataSourceContextHolder.write();
            log.debug("dataSource切换到：write");
        } else {
            DataSourceContextHolder.read();
            log.debug("dataSource切换到：Read");
        }
    }
}
