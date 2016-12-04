/**
 * UserServiceImpl.java 2016年11月19日 17:49
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.framework.sso.service.user;

import com.ql.cloud.commons.base.model.PageInfo;
import com.ql.cloud.framework.sso.dao.user.UserDao;
import com.ql.cloud.framework.sso.entity.user.User;
import com.ql.cloud.framework.sso.iservice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * userService
 *
 * @author 齐龙
 * @date 2016年11月19日 17:49
 */
@RestController
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo<User> getUserList(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "page", required = false) Integer page) {
        PageInfo<User> pageInfo = new PageInfo<>(page);
        Map<String, Object> sqlMap = new HashMap<>();
        sqlMap.put("username", username);
        sqlMap.put("pageInfo", pageInfo);
        List<User> users = userDao.getList(sqlMap);
        int countUser = userDao.getCount(sqlMap);
        pageInfo.setResultList(users);
        pageInfo.setTotalNum(countUser);
        return pageInfo;
    }

    @Override
    @Transactional
    public Integer insertUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.insert(user);
        return user.getId();
    }

    @Override
    public Boolean checkUser(@RequestParam("username") String username) {
        return true;
    }
}
