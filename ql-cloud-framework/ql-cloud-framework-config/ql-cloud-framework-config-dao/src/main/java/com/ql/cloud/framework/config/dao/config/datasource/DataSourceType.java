/**
 * DataSourceType.java 2016年11月15日 11:23
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.config.datasource;

/**
 * 递归
 *
 * @author 齐龙
 * @date 2016年11月15日 11:23
 *
 */
public enum DataSourceType {
    read("read", "从库"), write("write", "主库");
    private String type;
    private String name;

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
