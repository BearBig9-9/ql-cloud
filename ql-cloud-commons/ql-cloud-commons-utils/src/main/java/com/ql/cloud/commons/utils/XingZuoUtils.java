/**
 * XingZuoUtils.java 2016年7月21日 下午4:24:45
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.util.Date;


/**
 * 星座工具类
 *
 * @author donghq
 * @date 2016年7月21日 下午4:24:45
 */
public class XingZuoUtils {

    @SuppressWarnings("deprecation")
    public static String getXingZuo(Date date) {
        int month = date.getMonth() + 1;
        int day = date.getDate();
        String xingzuo = null;
        switch (month) {
            case 1:
                if (day < 20) {
                    xingzuo = "摩羯座";
                } else {
                    xingzuo = "水瓶座";
                }
                break;

            case 2:
                if (day < 19) {
                    xingzuo = "水瓶座";
                } else {
                    xingzuo = "双鱼座";
                }
                break;
            case 3:
                if (day < 21) {
                    xingzuo = "双鱼座";
                } else {
                    xingzuo = "白羊座";
                }
                break;
            case 4:
                if (day < 20) {
                    xingzuo = "白羊座";
                } else {
                    xingzuo = "金牛座";
                }
                break;
            case 5:
                if (day < 21) {
                    xingzuo = "金牛座";
                } else {
                    xingzuo = "双子座";
                }
                break;
            case 6:
                if (day < 22) {
                    xingzuo = "双子座";
                } else {
                    xingzuo = "巨蟹座";
                }
                break;
            case 7:
                if (day < 23) {
                    xingzuo = "巨蟹座";
                } else {
                    xingzuo = "狮子座";
                }
                break;
            case 8:
                if (day < 23) {
                    xingzuo = "狮子座";
                } else {
                    xingzuo = "处女座";
                }
                break;
            case 9:
                if (day < 23) {
                    xingzuo = "处女座";
                } else {
                    xingzuo = "天枰座";
                }
                break;
            case 10:
                if (day < 24) {
                    xingzuo = "天枰座";
                } else {
                    xingzuo = "天蝎座";
                }
                break;
            case 11:
                if (day < 22) {
                    xingzuo = "天蝎座";
                } else {
                    xingzuo = "射手座";
                }
                break;
            case 12:
                if (day < 22) {
                    xingzuo = "射手座";
                } else {
                    xingzuo = "摩羯座";
                }
                break;

        }
        return xingzuo;
    }
}
