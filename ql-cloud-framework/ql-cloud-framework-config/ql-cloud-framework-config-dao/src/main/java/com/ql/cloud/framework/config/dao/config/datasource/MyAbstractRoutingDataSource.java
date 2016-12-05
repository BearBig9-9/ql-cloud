/**
 * MyAbstractRoutingDataSource.java 2016年11月15日 11:26
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多数据源切换
 *
 * @author 齐龙
 * @date 2016年11月15日 11:26
 *
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

    private final int dataSourceNumber;
    /* 这是线程安全的 */
    private AtomicInteger count = new AtomicInteger(0);

    public MyAbstractRoutingDataSource(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getJdbcType();
        if (typeKey.equals(DataSourceType.write.getType()))
            return DataSourceType.write.getType();
        // 读 简单负载均衡
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        return new Integer(lookupKey);
    }

    public int getDataSourceNumber() {
        return dataSourceNumber;
    }
}
