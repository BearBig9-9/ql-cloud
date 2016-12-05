/**
 * RoutingDataSourceHealthIndicator.java 2016年12月05日 14:09
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.health;

import com.ql.cloud.commons.base.component.SpringContextHolder;
import com.ql.cloud.framework.config.dao.config.datasource.MyAbstractRoutingDataSource;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import javax.sql.DataSource;

/**
 * 数据源健康值监控
 *
 * @author 齐龙
 * @date 2016年12月05日 14:09
 *
 */
public class RoutingDataSourceHealthIndicator implements HealthIndicator {
    private MyAbstractRoutingDataSource roundRobinDataSourceProxy;

    public RoutingDataSourceHealthIndicator(MyAbstractRoutingDataSource roundRobinDataSourceProxy) {
        this.roundRobinDataSourceProxy = roundRobinDataSourceProxy;
    }

    @Override
    public Health health() {
        DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");
        if (writeDataSource != null && roundRobinDataSourceProxy != null) {
            return Health.up().withDetail("writeDataSource", "true")
                    .withDetail("readDataSourceNum", roundRobinDataSourceProxy.getDataSourceNumber()).build();
        } else {
            Health.Builder builder = Health.down();
            if (writeDataSource == null) {
                builder.withDetail("writeDataSource", "false");
            }
            if (roundRobinDataSourceProxy == null) {
                builder.withDetail("roundRobinDataSourceProxy", "false");
            }
            return builder.build();
        }

    }
}
