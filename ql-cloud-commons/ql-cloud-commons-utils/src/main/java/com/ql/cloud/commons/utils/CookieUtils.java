/**
 * CookieUtils.java 2016年7月21日 下午5:37:14
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import com.ql.cloud.commons.utils.encrypt.DesEncrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 格式校验工具类
 *
 * @author donghq
 * @date 2016年7月21日 下午5:37:14
 */
public class CookieUtils {


    /**
     * 根据名字获取cookie
     * 
     * @param request
     * @param name
     * @return string
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        if (name == null && "".equals(name))
            return "";
        Cookie[] cookies = request.getCookies();
        try {
            if (null != cookies) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie value = cookies[i];
                    if (name.equals(value.getName())) {
                        return URLDecoder.decode(value.getValue(), "utf-8");
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name
     * @return string
     */
    public static String getCookieByNameWithUnescape(HttpServletRequest request, String name) {
        if (name == null && "".equals(name))
            return "";
        Cookie[] cookies = request.getCookies();
        String result = null;
        try {
            if (null != cookies) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie value = cookies[i];
                    if (name.equals(value.getName())) {
                        result = URLDecoder.decode(EscapeUtils.unescape(value.getValue()), "utf-8");
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加cookie
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     * @return
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0)
            cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 二级域名cookie共享
     * 
     * @param response
     * @param name
     * @param value
     * @param domain
     * @param maxAge
     */
    public static void addCookieByDomain(HttpServletResponse response, String name, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath("/");
        if (maxAge > 0)
            cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void addDesEncryptCookie(HttpServletResponse response, String name, String value, int maxAge) throws Exception {
        Cookie cookie = new Cookie(name, DesEncrypt.getInstance().desEncrypt(value));
        cookie.setPath("/");
        if (maxAge > 0)
            cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static String getDesEncryptCookieByName(HttpServletRequest request, String name) throws Exception {
        if (name == null && "".equals(name))
            return "";
        Cookie[] cookies = request.getCookies();
        try {
            if (null != cookies) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie value = cookies[i];
                    if (name.equals(value.getName())) {
                        return DesEncrypt.getInstance().desDecrypt(URLDecoder.decode(value.getValue(), "utf-8"));
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static void delCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 域名下删除cookie
     * 
     * @param response
     * @param name
     * @param domain
     */
    public static void delCookieByDomain(HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


    /**
     * 将cookie封装到Map里面
     * 
     * @param request
     * @return
     */
    public static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
