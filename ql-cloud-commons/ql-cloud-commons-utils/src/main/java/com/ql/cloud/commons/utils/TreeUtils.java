/**
 * TreeUtils.java 2016年7月22日 上午09:24:16
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * URL工具类
 *
 * @author liusn
 * @date 2016年7月22日 上午09:24:17
 *
 */
public class TreeUtils {

    /**
     * 返回菜单级别名称，默认支持5级
     */
    public static String getLevelName(Integer level, String name) {
        if (StringUtils.isNotBlank(name) && null != level) {
            switch (level) {
                case -1:
                    name = "|--" + name;
                    break;
                case 0:
                    name = "|--|--" + name;
                    break;
                case 1:
                    name = "|--|--|--" + name;
                    break;
                case 2:
                    name = "|--|--|--|--" + name;
                    break;
                case 3:
                    name = "|--|--|--|--|--" + name;
                    break;
                case 4:
                    name = "|--|--|--|--|--|--" + name;
                    break;
                case 5:
                    name = "|--|--|--|--|--|--|--" + name;
                    break;
                default:
                    break;
            }
        }
        return name;
    }

    /**
     * 总部： 0000000000 (level:-1) 一级部门：0100000000 (level:0) 二级部门：0101000000 (level:1) 三级部门：0101010000
     * (level:2) 四级部门：0101010100 (level:3)
     * */
    public static String countCode(String parentCode, String superiorCode, int currLevel) throws RuntimeException {
        String resultStr = null;
        String currCode = null;
        if (!(StringUtils.isNotBlank(superiorCode))) {
            switch (currLevel) {
                case 0:
                    resultStr = "0100000000";
                    break;
                case 1:
                    resultStr = parentCode.substring(0, 2) + "01000000";
                    break;
                case 2:
                    resultStr = parentCode.substring(0, 4) + "010000";
                    break;
                case 3:
                    resultStr = parentCode.substring(0, 6) + "0100";
                    break;
                default:
                    break;
            }
        } else {
            switch (currLevel) {
                case 0:
                    currCode = (Integer.parseInt(superiorCode.substring(0, 2)) + 1) + "";
                    currCode = fullZero(0, currCode);
                    resultStr = currCode + superiorCode.substring(2, 10);
                    break;
                case 1:
                    currCode = (Integer.parseInt(superiorCode.substring(0, 4)) + 1) + "";
                    currCode = fullZero(1, currCode);
                    resultStr = currCode + superiorCode.substring(4, 10);
                    break;
                case 2:
                    currCode = (Integer.parseInt(superiorCode.substring(0, 6)) + 1) + "";
                    currCode = fullZero(2, currCode);
                    resultStr = currCode + superiorCode.substring(6, 10);
                    break;
                case 3:
                    currCode = (Integer.parseInt(superiorCode) + 1) + "";
                    currCode = fullZero(3, currCode);
                    resultStr = currCode;
                    break;
                default:
                    break;
            }
        }
        return resultStr;
    }

    private static String fullZero(int level, String currCode) {
        switch (level) {
            case 0:
                if (2 != currCode.length()) {
                    return "0" + currCode;
                }
                break;
            case 1:
                if (4 != currCode.length()) {
                    StringBuffer zero = new StringBuffer();
                    for (int i = 0; i < (4 - currCode.length()); i++) {
                        zero.append("0");
                    }
                    return zero + currCode;
                }
                break;
            case 2:
                if (6 != currCode.length()) {
                    StringBuffer zero = new StringBuffer();
                    for (int i = 0; i < (6 - currCode.length()); i++) {
                        zero.append("0");
                    }
                    return zero + currCode;
                }
                break;
            case 3:
                if (8 != currCode.length()) {
                    StringBuffer zero = new StringBuffer();
                    for (int i = 0; i < (8 - currCode.length()); i++) {
                        zero.append("0");
                    }
                    return zero + currCode;
                }
                break;
            default:
                break;
        }
        return currCode;
    }
}
