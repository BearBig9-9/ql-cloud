/**
 * SysContent.java 2016年7月22日 上午09:24:16
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author bzlc
 * @date 3.0 2016年7月22日 上午09:24:17
 *
 */
public class SysContent {

    /** The request local. */
    private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();

    /** The response local. */
    private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

    /**
     * Gets the request.
     *
     * @return the request
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) requestLocal.get();
    }

    /**
     * Sets the request.
     *
     * @param request the new request
     */
    public static void setRequest(HttpServletRequest request) {
        requestLocal.set(request);
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) responseLocal.get();
    }

    /**
     * Sets the response.
     *
     * @param response the new response
     */
    public static void setResponse(HttpServletResponse response) {
        responseLocal.set(response);
    }

    /**
     * Gets the session.
     *
     * @return the session
     */
    public static HttpSession getSession() {
        return (HttpSession) ((HttpServletRequest) requestLocal.get()).getSession();
    }
}
