/**
 * ListUtils.java 2016年7月21日 下午4:24:45
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * List工具类
 *
 * @author donghq
 * @date 2016年7月21日 下午4:24:45
 */
public class ListUtils {

    /**
     * 去除重复元素
     *
     * @param list
     * @return list
     */
    public static List removeDuplicate(List list) {
        Set<Integer> primesWithoutDuplicates = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(primesWithoutDuplicates);
        return list;
    }
}
