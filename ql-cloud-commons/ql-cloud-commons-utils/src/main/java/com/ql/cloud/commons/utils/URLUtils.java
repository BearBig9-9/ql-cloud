/**
 * URLUtils.java 2016年7月22日 上午09:24:16
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * URL工具类
 * 
 * @author donghq
 * @date 2016年7月22日 上午09:24:17
 * 
 */
public class URLUtils {

    public static final String HTTP = "http://";
    public static final int PORT_80 = 80;
    private static final Logger log = LoggerFactory.getLogger(URLUtils.class);

    public static String getAbsoluteContextPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return HTTP + request.getServerName() + (PORT_80 == request.getServerPort() ? "" : ":" + request.getServerPort())
                + request.getContextPath();
    }

    public static String getContextPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getContextPath();
    }

    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static HttpSession getHttpSession() {
        return URLUtils.getHttpServletRequest().getSession();
    }

    public static String getdomainName() {
        String serverName = URLUtils.getHttpServletRequest().getServerName();
        int indexPosition = serverName.indexOf(".");
        if (indexPosition == -1) {
            return "";
        }
        String domainName = serverName.substring(0, indexPosition);
        return domainName;
    }

    public static HttpServletResponse getHttpServletResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 获取GET|POST请求参数
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Map<String, String> getRequestMapParam(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Enumeration<?> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if (paramValues.length == 1) {
                    String paramValue = paramValues[0];
                    map.put(paramName, paramValue);
                }
            }
        } catch (Exception e) {
            log.error("操作获取请求参数出错:", e);
            throw new Exception("操作获取请求参数出错:" + e.getMessage());
        } finally {
            return map;
        }
    }

    /**
     * 参数变为map:
     * "respCode=00&tn=201508260554137671888&signMethod=MD5&transType=01&charset=UTF-8&signature=2814d5f099220cddba76c90cf20de285&version=1.0.0"
     * ;
     *
     * @param strParam
     * @return
     */
    public static Map<String, String> getParam2Map(String strParam) {
        if (strParam == null || strParam.equals("")) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        String[] params = strParam.split("&");
        for (String item : params) {
            String[] keyValue = item.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    /**
     * 设置HTTP响应头属性不要缓存
     *
     *
     * */
    public static void setResponseNoCache() {
        HttpServletResponse response = URLUtils.getHttpServletResponse();
        URLUtils.setResponseNoCache(response);
    }


    /**
     * 设置HTTP响应头属性不要缓存
     *
     * @param response
     * */
    public static void setResponseNoCache(HttpServletResponse response) {
        if (response == null)
            return;
        response.setHeader("Cache-Control", "no-cache, no-store, private, must-revalidate, proxy-revalidate");
        response.setHeader("Pragma", "no-cache");
    }

    /**
     * 判断是否ajax访问
     * 
     * @return
     */
    public static boolean isAjax() {
        HttpServletRequest request = getHttpServletRequest();
        String xmlRequest = request.getHeader("X-Requested-With");
        return (xmlRequest != null && "xmlhttprequest".equals(xmlRequest.toLowerCase()));
    }

    /**
     * 获取访问ip
     * 
     * @return
     */
    public static String getIp() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 清理session
     */
    public static void clearSession() {
        HttpSession session = getHttpSession();
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            session.removeAttribute(key);
        }
    }

    /**
     * 去空value
     *
     * @param sArray
     * @return
     */
    public static SortedMap<String, String> filterMap(Map<String, String> sArray) {

        SortedMap<String, String> result = new TreeMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 随机获取影院ip
     * 
     * @param cinemaIps
     * @return
     */
    public static String randomGetCinemaIp(String[] cinemaIps) {
        if (cinemaIps != null && cinemaIps.length > 0){
            if (cinemaIps.length == 1){
                return cinemaIps[0];
            } else {
                int length = cinemaIps.length;
                Random ra = new Random();
                int t = ra.nextInt(length);
                return cinemaIps[t];
            }
        }
        return null;
    }
}
