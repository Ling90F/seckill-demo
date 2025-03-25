package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.SeckillTime;

import java.util.List;

/**
 * @author
 */
public interface SeckillTimeService {

    /**
     * SeckillTime多条件分页查询
     */
    PageInfo<SeckillTime> findPage(SeckillTime seckillTime, int page, int size);

    /**
     * SeckillTime分页查询
     */
    PageInfo<SeckillTime> findPage(int page, int size);

    /**
     * SeckillTime多条件搜索方法
     */
    List<SeckillTime> findList(SeckillTime seckillTime);

    /**
     * 删除SeckillTime
     */
    void delete(Integer id);

    /**
     * 修改SeckillTime数据
     */
    void update(SeckillTime seckillTime);

    /**
     * 新增SeckillTime
     */
    void add(SeckillTime seckillTime);

    /**
     * 根据ID查询SeckillTime
     */
    SeckillTime findById(Integer id);

    /**
     * 查询所有SeckillTime
     */
    List<SeckillTime> findAll(String name);

    /**
     * 审核
     */
    void audit(Integer id, Integer type);

    /**
     * 查询有效时间
     */
    List<SeckillTime> findAllValidTimes();

    /**
     * 删除所有时间段
     */
    void deleteAll();

    /**
     * 批量增加时间段
     */
    void addTimes(List<SeckillTime> seckillTimes);

}
