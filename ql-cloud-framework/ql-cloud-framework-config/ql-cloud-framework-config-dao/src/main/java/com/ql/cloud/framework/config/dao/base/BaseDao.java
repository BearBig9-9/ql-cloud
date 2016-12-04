/**
 * BaseDao.java 2016年11月19日 16:35
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.config.dao.base;

import java.util.List;
import java.util.Map;

/**
 * baseDao接口
 *
 * @author 齐龙
 * @date 2016年11月19日 16:35
 */
public interface BaseDao<T, ID> {
    public Integer insert(T entity);

    public Integer update(T entity);

    public Integer updateByMap(Map<String, Object> sqlMap);

    public T getById(ID id);

    public Integer getByMap(Map<String, Object> sqlMap);

    public Integer getCount(Map<String, Object> sqlMap);

    public List<T> getList(Map<String, Object> sqlMap);
}
