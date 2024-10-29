package com.seckill.order.service;

import com.github.pagehelper.PageInfo;
import com.seckill.order.pojo.Order;

import java.io.IOException;
import java.util.Map;

/**
 * @author http://www.itheima.com
 */
public interface OrderService {

    /**
     * Order多条件分页查询
     */
    PageInfo<Order> findPage(Order order, int page, int size);

    Order findById(String id);

    void pay(String id);
}
