package com.seckill.manager.service;

import com.seckill.manager.pojo.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author http://www.itheima.com
 */
public interface AdminService {

    Admin findByName(String username);
}
