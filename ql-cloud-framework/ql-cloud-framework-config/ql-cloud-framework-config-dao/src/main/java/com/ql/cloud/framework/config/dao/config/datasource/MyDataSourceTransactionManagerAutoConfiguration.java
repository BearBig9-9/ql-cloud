/**
 * MyDataSourceTransactionManagerAutoConfiguration.java 2016年11月15日 11:41
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.datasource;

import com.ql.cloud.commons.base.component.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 自定义事务
 *
 * @author 齐龙
 * @date 2016年11月15日 11:41
 *
 */
@Configuration
@EnableTransactionManagement
public class MyDataSourceTransactionManagerAutoConfiguration extends DataSourceTransactionManagerAutoConfiguration {
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 自定义事务 MyBatis自动参与到spring事务管理中，无需额外配置，只要org.mybatis.spring.
     * SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，否则事务管理会不起作用。
     * 
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManagers() {
        log.info("-------------------- transactionManager init ---------------------");
        return new DataSourceTransactionManager(SpringContextHolder.getBean("roundRobinDataSourceProxy"));
    }
}
