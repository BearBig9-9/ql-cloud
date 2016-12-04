/**
 * CheckFormatUtils.java 2016年7月21日 下午5:28:33
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

import org.apache.commons.lang3.StringUtils;

/**
 * 格式校验工具类
 *
 * @author donghq
 * @date 2016年7月21日 下午5:28:33
 */
public class CheckFormatUtils {

    /**
     * 检查手机号格式
     * 
     * 中国移动： 2G 号段： 134 、 135 、 136 、 137 、 138 、 139 、 150 、 151 、 152 、 157 、 158 、 159 ； 3G 号段：
     * 182 、 183 、 184 、 187 、 188 、 178 、 147 中国联通： 2G 号段： 130 、 131 、 132 、 155 、 156 ； 3G 号段： 185
     * 、 186 、 176 、 145 中国电信： 2G 号段： 133 、 153 ； 3G 号段： 180 、 181 、 189 、 177 卫星通信： 1349 虚拟运营商： 170
     * 
     * @param mobile
     * @return
     * @author donghq
     */
    public static boolean isMobileNo(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        Pattern p = Pattern.compile(PatternUtils.MOBILE_PHONE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 检查邮箱格式
     * 
     * @param mail
     * @return boolean
     * 
     */
    public static boolean isMail(String mail) {
        if (StringUtils.isBlank(mail)) {
            return false;
        }
        Pattern p = Pattern.compile(PatternUtils.EMAIL);
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    /**
     * 密码格式验证(6-20位)
     * 
     * @param password
     * @return boolean
     */
    public static boolean isPasswdOk(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }
        if (password.length() < 6 || password.length() > 20) {
            return false;
        }
        return true;
    }

    public static boolean isNumber(String param) {
        if (StringUtils.isBlank(param)) {
            return false;
        }
        return true;
    }
}
