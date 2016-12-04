/**
 * UserController.java 2016年11月19日 23:17
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.sso.web.controller;

import com.ql.cloud.commons.base.model.JsonResult;
import com.ql.cloud.commons.base.model.PageInfo;
import com.ql.cloud.framework.sso.entity.user.User;
import com.ql.cloud.framework.sso.iservice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * userController
 *
 * @author 齐龙
 * @date 2016年11月19日 23:17
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(String username, Integer page) {
        PageInfo<User> userPageInfo = userService.getUserList(username, page);
        return new JsonResult(JsonResult.CODE_SUCCESS, JsonResult.MSG_SUCCESS, userPageInfo);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JsonResult save(String username, String password){
        Integer userId = userService.insertUser(username, password);
        return new JsonResult(JsonResult.CODE_SUCCESS, JsonResult.MSG_SUCCESS, userId);
    }

}
