package com.seckill.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.feign.SkuFeign;
import com.seckill.goods.pojo.Sku;
import com.seckill.order.dao.OrderMapper;
import com.seckill.order.pojo.Order;
import com.seckill.order.service.OrderService;
import com.seckill.util.IdWorker;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import com.seckill.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 秒杀下单
     * @param orderMap
     */
    @Override
    public void addHotOrder(Map<String, String> orderMap) {
        String id = orderMap.get("id");
        String username = orderMap.get("username");
        //key
        String key = "SKU_" + id;
        //用户购买的key
        String userKey = "USER" + username + "ID" + id;

        if (redisTemplate.hasKey(key)) {
            //数量
            Integer num = Integer.parseInt(redisTemplate.boundHashOps(key).get("num").toString());

            //拥有库存，执行递减操作
            if (num > 0) {
                //查询商品
                Result<Sku> result = skuFeign.findById(id);
                Sku sku = result.getData();
                Order order = new Order();
                order.setCreateTime(new Date());
                order.setUpdateTime(order.getCreateTime());
                order.setUsername(username);
                order.setSkuId(id);
                order.setName(sku.getName());
                order.setPrice(sku.getSeckillPrice());
                order.setId("No" + idWorker.nextId());
                order.setTotalNum(1);
                order.setOrderStatus("0");
                order.setPayStatus("0");
                order.setConsignStatus("0");
                orderMapper.insertSelective(order);

                //库存递减
                num--;

                if (num == 0) {
                    //同步数据到数据库，秒杀数量归零
                    skuFeign.zero(id);
                }

                //更新数据
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("num", num);
                dataMap.put(userKey, 0);

                //存数据
                redisTemplate.boundHashOps(key).putAll(dataMap);
            }

            //记录该商品用户24小时内无法再次购买,测试环境，我们只配置成1分钟
            redisTemplate.boundValueOps(userKey).set("");
            redisTemplate.expire(userKey, 1, TimeUnit.MINUTES);
        }
    }


    /**
     * 添加订单
     */
    @Override
    public int add(Order order) {
        String userKey = "USER" + order.getUsername() + "ID" + order.getSkuId();
        //1.递减库存
        Result<Sku> dcount = skuFeign.dcount(order.getSkuId(), order.getTotalNum());
        //2.递减成功->下单->记录当前用户抢单的时间点，24小时内不能抢购该商品
        if (dcount.getCode() == StatusCode.DECOUNT_OK) {

            Sku sku = dcount.getData();
            //下单
            order.setOrderStatus("0");
            order.setPayStatus("0");
            order.setConsignStatus("0");
            order.setSkuId(sku.getId());
            order.setName(sku.getName());
            order.setPrice(sku.getSeckillPrice() * order.getTotalNum());
            orderMapper.insertSelective(order);
            //记录当前用户抢单的时间点，24小时内不能抢购该商品
            redisTemplate.boundValueOps(userKey).set("");
            redisTemplate.boundValueOps(userKey).expire(1, TimeUnit.DAYS);
            return StatusCode.ORDER_OK;
        } else {
            //3.递减失败
            //405库存不足
            if (dcount.getCode() == StatusCode.DECOUNT_NUM) {
                return StatusCode.DECOUNT_NUM;
            } else if (dcount.getCode() == StatusCode.DECOUNT_HOT) {
                //205商品热点
                String key = "SKU_" + order.getSkuId();
                Long increment = redisTemplate.boundHashOps(key).increment(userKey, 1);
                if (increment == 1) {
                    //执行排队
                    Map<String, String> queueMap = new HashMap<>();
                    queueMap.put("username", order.getUsername());
                    queueMap.put("id", order.getSkuId());
                    kafkaTemplate.send("neworder", JSON.toJSONString(queueMap));
                }
                return StatusCode.ORDER_QUEUE;
            }

            return dcount.getCode();
        }
    }



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
