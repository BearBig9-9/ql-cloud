/**
 * StringUtil.java 2016年10月08日 10:44
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.commons.utils;

/**
 * 字符串操作
 *
 * @author 齐龙
 * @date 2016年10月08日 10:44
 *
 */
public class StringUtil {
    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    // 首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static String appendZreo(Integer count, String fromStr) {
        Integer length = null == fromStr ? 0 : fromStr.length();
        if(length >= count){
            return fromStr;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count - length; i++) {
            sb.append("0");
        }
        sb.append(null == fromStr ? "" : fromStr);
        return sb.toString();
    }
}
