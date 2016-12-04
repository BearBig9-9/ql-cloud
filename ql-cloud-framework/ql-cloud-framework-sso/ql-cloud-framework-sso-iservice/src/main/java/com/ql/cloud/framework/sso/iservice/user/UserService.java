/**
 * UserService.java 2016年11月19日 17:41
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.sso.iservice.user;

import com.ql.cloud.commons.base.model.PageInfo;
import com.ql.cloud.framework.sso.entity.user.User;
import com.ql.cloud.framework.sso.hystrix.user.UserHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * userService接口
 *
 * @author 齐龙
 * @date 2016年11月19日 17:41
 */
@FeignClient(value = "sso-server", fallback = UserHystrixImpl.class)
public interface UserService {
    /**
     * 查询用户分页
     *
     * @param username
     * @param page
     * @return
     */
    @RequestMapping(value = "user/getUserList", method = RequestMethod.GET)
    public PageInfo<User> getUserList(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "page", required = false) Integer page);

    /**
     * 插入User
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "user/insertUser", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Integer insertUser(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "user/checkUser", method = RequestMethod.GET)
    public Boolean checkUser(@RequestParam("username") String username);
}
