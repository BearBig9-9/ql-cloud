/**
 * ExpandStringUtils.java 2016年7月21日 下午5:28:33
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author 齐龙
 * @version 3.0 2016年7月21日 下午5:28:33
 *
 */
public class ExpandStringUtils {
    /**
     * 判断字符串是否包括汉字
     * 
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 取字符串中指定的内容
     * 
     * @param regStr
     * @param content
     * @return
     */
    public static String pre_match(String regStr, String content) {
        String result = null;
        Pattern p = Pattern.compile(regStr);
        Matcher m = p.matcher(content);
        while (m.find()) {
            result = m.group(1);
        }
        return result;
    }

    /**
     * 去除字符串中所有空格
     *
     * @param str
     * @return
     */
    public static String trimAll(String str) {
        Pattern p = Pattern.compile("\\s*");
        Matcher m = p.matcher(str);
        String strNoBlank = m.replaceAll("");
        return strNoBlank;
    }
}
