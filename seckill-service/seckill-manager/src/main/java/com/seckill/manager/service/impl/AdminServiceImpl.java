package com.seckill.manager.service.impl;

import com.seckill.manager.dao.AdminMapper;
import com.seckill.manager.pojo.Admin;
import com.seckill.manager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /****
     * 根据名字查询管理员
     */
    @Override
    public Admin findByName(String username) {
        Admin admin = new Admin();
        admin.setLoginName(username);
        return adminMapper.selectOne(admin);
    }
}
