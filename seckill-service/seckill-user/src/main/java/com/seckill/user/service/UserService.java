package com.seckill.user.service;

import com.github.pagehelper.PageInfo;
import com.seckill.user.pojo.User;

public interface UserService {

    /**
     * 根据ID查询User
     * @return
     */
    User findById(String id);

    /**
     * User多条件分页查询
     */
    PageInfo<User> findPage(User user, int page, int size);

}
