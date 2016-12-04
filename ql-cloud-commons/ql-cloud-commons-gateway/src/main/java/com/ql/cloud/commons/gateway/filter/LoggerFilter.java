/**
 * LoggerFilter.java 2016年11月27日 14:42
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.commons.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import com.ql.cloud.framework.sso.iservice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志拦截器
 *
 * @author 齐龙
 * @date 2016年11月27日 14:42
 */
@Component
@ConfigurationProperties(prefix = "filter.logger")
public class LoggerFilter extends BaseFilter {

    @Autowired
    private UserService userService;

    /**
     * run：过滤器的具体逻辑。
     * 需要注意，这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，
     * 不对其进行路由，然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，
     * 当然我们也可以进一步优化我们的返回，
     * 比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("{} request to {}", request.getMethod(), request.getRequestURL().toString());
        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            logger.warn("access token is empty or wrong!");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody(userService.checkUser("ttt")?"1":"0");
            return null;
        }
        logger.info("access token ok");
        return null;
    }

}
