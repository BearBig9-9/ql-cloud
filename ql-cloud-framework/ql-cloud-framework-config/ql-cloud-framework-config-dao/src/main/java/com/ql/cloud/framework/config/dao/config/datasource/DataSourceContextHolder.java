/**
 * DataSourceContextHolder.java 2016年11月15日 11:22
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.datasource;

/**
 * 数据源容器
 *
 * @author 齐龙
 * @date 2016年11月15日 11:22
 *
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 读可能是多个库
     */
    public static void read() {
        local.set(DataSourceType.read.getType());
    }

    /**
     * 写只有一个库
     */
    public static void write() {
        local.set(DataSourceType.write.getType());
    }

    public static String getJdbcType() {
        return local.get();  // == null ? DataSourceType.write.getType() : local.get()
    }
}
