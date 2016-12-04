/**
 * CustomHttpRequestRetryHandler.java 2016年09月29日 13:53
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.commons.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http重试控制器
 *
 * @author 齐龙
 * @date 2016年09月29日 13:53
 *
 */
public class CustomHttpRequestRetryHandler implements HttpRequestRetryHandler {
    Logger logger = LoggerFactory.getLogger(getClass());
    private int maxRetryTimes;
    public CustomHttpRequestRetryHandler(int maxRetryTimes){

        this.maxRetryTimes = maxRetryTimes;
    }
    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Http重试线程sleep异常:{}", ExceptionUtils.getStackTrace(e));
        }
        if (executionCount >= maxRetryTimes) {
            // Do not retry if over max retry count
            return false;
        }
        if (exception instanceof InterruptedIOException) {
            // Timeout
            return false;
        }
        if (exception instanceof UnknownHostException) {
            // Unknown host
            return false;
        }
        if (exception instanceof ConnectTimeoutException) {
            // Connection refused
            return false;
        }
        if (exception instanceof SSLException) {
            // SSL handshake exception
            return false;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
        if (idempotent) {
            // Retry if the request is considered idempotent
            return true;
        }
        return false;
    }
}
