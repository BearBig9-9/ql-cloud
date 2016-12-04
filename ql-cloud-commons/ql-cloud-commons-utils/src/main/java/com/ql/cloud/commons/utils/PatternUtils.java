/**
 * PatternUtils.java 2016年09月02日 20:53
 * <p/>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.commons.utils;

/**
 * 类说明
 *
 * @author 张凯停
 * @date 2016年09月02日 20:53
 */
public class PatternUtils {
    public static final String MOBILE_PHONE = "^((13[0-9])|(14[57])|(15[^4,\\D])|(17[0678])|(18[0-9]))\\d{8}$";
    public static final String EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public static final String DATE = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
    public static final String DATE_TIME = "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";
    public static final String URL="(https?|http)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    public static final String FAX= "^(\\d{3,4}-)?\\d{7,8}$";
    public static final String NUMBER = "^\\+?[1-9][0-9]*$";
    public static final String TEL_PHONE= "^((\\d{3,4})|\\d{3,4}-)?\\d{7,8}$";
    public static final String POST_CODE = "[1-9]\\d{5}(?!\\d)";
}
