/**
 * UserHystrixImpl.java 2016年11月19日 17:42
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.sso.hystrix.user;

import com.ql.cloud.commons.base.model.PageInfo;
import com.ql.cloud.framework.sso.entity.user.User;
import com.ql.cloud.framework.sso.iservice.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户断路由
 *
 * @author 齐龙
 * @date 2016年11月19日 17:42
 */
@Component
public class UserHystrixImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public PageInfo<User> getUserList(String username, Integer page) {
        logger.info("[执行断路由]-{}", "getUserList");
        return null;
    }

    @Override
    public Integer insertUser(String username, String password) {
        logger.info("[执行断路由]-{}", "insertUser");
        return null;
    }

    @Override
    public Boolean checkUser(@RequestParam("username") String username) {
        logger.info("[执行断路由]-{}", "checkUser");
        return false;
    }
}
