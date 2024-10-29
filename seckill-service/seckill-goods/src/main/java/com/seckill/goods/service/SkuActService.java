package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.pojo.SkuAct;

import java.util.List;

/**
 * @author http://www.itheima.com
 */
public interface SkuActService {

    /**
     * SkuAct多条件分页查询
     */
    PageInfo<SkuAct> findPage(SkuAct skuAct, int page, int size);

    /**
     * SkuAct分页查询
     */
    PageInfo<SkuAct> findPage(int page, int size);

    /**
     * SkuAct多条件搜索方法
     */
    List<SkuAct> findList(SkuAct skuAct);

    /**
     * 删除SkuAct
     */
    void delete(String id);

    /**
     * 修改SkuAct数据
     */
    void update(SkuAct skuAct);

    /**
     * 新增SkuAct
     */
    void add(SkuAct skuAct);

    /**
     * 根据ID查询SkuAct
     */
    SkuAct findById(String id);

    /**
     * 查询所有SkuAct
     */
    List<SkuAct> findAll();

    /**
     * 删除所有商品和活动的关系
     */
    void deleteAll();

    /**
     * 增加活动下的商品
     */
    void addList(Activity activity, List<Sku> skuList) throws Exception;

}
