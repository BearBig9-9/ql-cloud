/**
 * NumberUtils.java 2014年11月18日 下午4:07:36
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.util.Random;


/**
 * 数字工具类
 *
 * @author liusn
 * @date 2014年11月18日 下午4:07:36
 */
public class NumberUtils {

    private static NumberUtils obj = new NumberUtils();

    private NumberUtils() {}

    public static NumberUtils getInstance() {
        if (null == obj) {
            return new NumberUtils();
        } else {
            return obj;
        }
    }

    /**
     * 返回手机验证码
     * */
    public String getVerifyCode() throws Exception {
        return new String(getRandomNum().append(getRandomNum()).append(getRandomNum()).append(getRandomNum()).append(getRandomNum())
                .append(getRandomNum()));
    }

    /**
     * 获取1-9随机整数
     * */
    private StringBuffer getRandomNum() throws Exception {
        return new StringBuffer(new Random().nextInt(10) + "");
    }

    /**
     * 获取一定长度的随机字符串
     * 
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
