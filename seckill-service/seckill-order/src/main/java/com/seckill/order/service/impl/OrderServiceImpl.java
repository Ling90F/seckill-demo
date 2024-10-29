package com.seckill.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.feign.SkuFeign;
import com.seckill.goods.pojo.Sku;
import com.seckill.order.dao.OrderMapper;
import com.seckill.order.pojo.Order;
import com.seckill.order.service.OrderService;
import com.seckill.util.IdWorker;
import com.seckill.util.Result;
import com.seckill.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author http://www.itheima.com
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private IdWorker idWorker;

    /**
     * Order条件+分页查询
     *
     * @param order 查询条件
     * @param page  页码
     * @param size  页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Order> findPage(Order order, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(order);

        // 排序
        example.orderBy("createTime").desc();

        //订单查询
        List<Order> orders = orderMapper.selectByExample(example);

        //查询每个订单的产品信息
        for (Order od : orders) {
            Result<Sku> skuResult = skuFeign.findById(od.getSkuId());
            if (skuResult.getData() != null) {
                od.setSku(skuResult.getData());
            }
        }

        //执行搜索
        return new PageInfo<>(orders);
    }

    @Override
    public Order findById(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public void pay(String id) {
        Order order = new Order();
        order.setId(id);
        order.setPayStatus("1");    //已支付
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * Order构建查询对象
     */
    private Example createExample(Order order) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if (order != null) {
            // 订单id
            if (!StringUtils.isEmpty(order.getId())) {
                criteria.andEqualTo("id", order.getId());
            }
            // 数量合计
            if (!StringUtils.isEmpty(order.getTotalNum())) {
                criteria.andEqualTo("totalNum", order.getTotalNum());
            }
            // 支付类型，1、在线支付、0 货到付款
            if (!StringUtils.isEmpty(order.getPayType())) {
                criteria.andEqualTo("payType", order.getPayType());
            }
            // 订单创建时间
            if (!StringUtils.isEmpty(order.getCreateTime())) {
                //criteria.andEqualTo("createTime", order.getCreateTime());
                criteria.andGreaterThanOrEqualTo("createTime", order.getCreateTime());
                //当天时间
                criteria.andLessThan("createTime", TimeUtil.addDateHour(order.getCreateTime(), 24));
            }
            // 订单更新时间
            if (!StringUtils.isEmpty(order.getUpdateTime())) {
                criteria.andEqualTo("updateTime", order.getUpdateTime());
            }
            // 付款时间
            if (!StringUtils.isEmpty(order.getPayTime())) {
                criteria.andEqualTo("payTime", order.getPayTime());
            }
            // 发货时间
            if (!StringUtils.isEmpty(order.getConsignTime())) {
                criteria.andEqualTo("consignTime", order.getConsignTime());
            }
            // 交易完成时间
            if (!StringUtils.isEmpty(order.getEndTime())) {
                criteria.andEqualTo("endTime", order.getEndTime());
            }
            // 交易关闭时间
            if (!StringUtils.isEmpty(order.getCloseTime())) {
                criteria.andEqualTo("closeTime", order.getCloseTime());
            }
            // 收货人
            if (!StringUtils.isEmpty(order.getReceiverContact())) {
                criteria.andEqualTo("receiverContact", order.getReceiverContact());
            }
            // 收货人手机
            if (!StringUtils.isEmpty(order.getReceiverMobile())) {
                criteria.andEqualTo("receiverMobile", order.getReceiverMobile());
            }
            // 收货人地址
            if (!StringUtils.isEmpty(order.getReceiverAddress())) {
                criteria.andEqualTo("receiverAddress", order.getReceiverAddress());
            }
            // 交易流水号
            if (!StringUtils.isEmpty(order.getTransactionId())) {
                criteria.andEqualTo("transactionId", order.getTransactionId());
            }
            // 订单状态,0:未完成,1:已完成，2：已退货
            if (!StringUtils.isEmpty(order.getOrderStatus())) {
                criteria.andEqualTo("orderStatus", order.getOrderStatus());
            }
            // 支付状态,0:未支付，1：已支付，2：支付失败
            if (!StringUtils.isEmpty(order.getPayStatus())) {
                criteria.andEqualTo("payStatus", order.getPayStatus());
            }
            // 发货状态,0:未发货，1：已发货，2：已收货
            if (!StringUtils.isEmpty(order.getConsignStatus())) {
                criteria.andEqualTo("consignStatus", order.getConsignStatus());
            }
            // 是否删除
            if (!StringUtils.isEmpty(order.getIsDelete())) {
                criteria.andEqualTo("isDelete", order.getIsDelete());
            }
            // 
            if (!StringUtils.isEmpty(order.getSkuId())) {
                criteria.andEqualTo("skuId", order.getSkuId());
            }
            // 
            if (!StringUtils.isEmpty(order.getName())) {
                criteria.andLike("name", "%" + order.getName() + "%");
            }
            // 
            if (!StringUtils.isEmpty(order.getPrice())) {
                criteria.andEqualTo("price", order.getPrice());
            }
            //
            if (!StringUtils.isEmpty(order.getUsername())) {
                criteria.andEqualTo("username", order.getUsername());
            }
        }
        return example;
    }
}
