package com.seckill.manager.service;

import com.seckill.manager.pojo.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author
 */
public interface AdminService {

    Admin findByName(String username);
}
