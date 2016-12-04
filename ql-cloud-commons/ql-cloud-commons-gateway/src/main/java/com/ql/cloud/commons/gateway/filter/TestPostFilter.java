/**
 * TestPostFilter.java 2016年11月27日 16:15
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.commons.gateway.filter;

import com.netflix.zuul.context.RequestContext;

/**
 * 测试用
 *
 * @author 齐龙
 * @date 2016年11月27日 16:15
 */
public class TestPostFilter extends BaseFilter {


    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("请求成功,serviceId:{},uri:{}", ctx.get("serviceId"), ctx.get("requestURI"));
        return null;
    }
}
