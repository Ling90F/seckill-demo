package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.SeckillTime;

import java.util.List;

/**
 * @author
 */
public interface ActivityService {

    /**
     * Activity多条件分页查询
     */
    PageInfo<Activity> findPage(Activity activity, int page, int size);

    /**
     * Activity分页查询
     */
    PageInfo<Activity> findPage(int page, int size);

    /**
     * Activity多条件搜索方法
     */
    List<Activity> findList(Activity activity);

    /**
     * 删除Activity
     */
    void delete(String id);

    /**
     * 修改Activity数据
     */
    void update(Activity activity);

    /**
     * 新增Activity
     */
    void add(Activity activity);

    /**
     * 根据ID查询Activity
     */
    Activity findById(String id);

    /**
     * 查询所有Activity
     */
    List<Activity> findAll();

    /**
     * 活动上线/下线
     */
    void isUp(String id, int isup);

    /**
     * 时间查询
     */
    List<Activity> times();

    /**
     * 创建活动
     */
    Activity createActivity(SeckillTime seckillTime);

    /**
     * 删除所有活动
     */
    void deleteAll();
}
