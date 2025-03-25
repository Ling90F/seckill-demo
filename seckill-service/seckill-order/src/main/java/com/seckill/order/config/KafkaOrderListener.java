package com.seckill.order.config;

import com.alibaba.fastjson.JSON;
import com.seckill.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaOrderListener {

    @Autowired
    private OrderService orderService;

    /***
     * 监听消息
     * 创建订单
     * @param message
     */
    @KafkaListener(topics = {"neworder"})
    public void receive(String message){
        //将消息转成Map
        Map<String,String> orderMap = JSON.parseObject(message,Map.class);
        //创建订单
        orderService.addHotOrder(orderMap);
    }
}