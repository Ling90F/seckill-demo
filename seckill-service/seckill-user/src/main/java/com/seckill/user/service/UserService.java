package com.seckill.user.service;

import com.github.pagehelper.PageInfo;
import com.seckill.user.pojo.User;

/**
 * @author http://www.itheima.com
 */
public interface UserService {

    /**
     * User多条件分页查询
     */
    PageInfo<User> findPage(User user, int page, int size);

}
