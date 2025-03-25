package com.seckill.order.service;

import com.github.pagehelper.PageInfo;
import com.seckill.order.pojo.Order;

import java.io.IOException;
import java.util.Map;


public interface OrderService {

    /**
     * 热点数据下单
     * @param orderMap
     */
    void addHotOrder(Map<String, String> orderMap);

    /**
     * 添加订单
     */
    int add(Order order);

    /**
     * Order多条件分页查询
     */
    PageInfo<Order> findPage(Order order, int page, int size);

    Order findById(String id);

    void pay(String id);
}
