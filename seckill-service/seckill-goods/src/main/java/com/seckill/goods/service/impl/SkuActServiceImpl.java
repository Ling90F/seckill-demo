package com.seckill.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.dao.ActivityMapper;
import com.seckill.goods.dao.SkuActMapper;
import com.seckill.goods.dao.SkuMapper;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.pojo.SkuAct;
import com.seckill.goods.service.SkuActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author
 */
@Service
public class SkuActServiceImpl implements SkuActService {

    @Autowired
    private SkuActMapper skuActMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * SkuAct条件+分页查询
     *
     * @param skuAct 查询条件
     * @param page   页码
     * @param size   页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SkuAct> findPage(SkuAct skuAct, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(skuAct);
        //执行搜索
        return new PageInfo<>(skuActMapper.selectByExample(example));
    }

    /**
     * SkuAct分页查询
     *
     * @param page 页码
     * @param size 页大小
     */
    @Override
    public PageInfo<SkuAct> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<>(skuActMapper.selectAll());
    }

    /**
     * SkuAct条件查询
     *
     * @param skuAct 查询条件
     */
    @Override
    public List<SkuAct> findList(SkuAct skuAct) {
        //构建查询条件
        Example example = createExample(skuAct);
        //根据构建的条件查询数据
        return skuActMapper.selectByExample(example);
    }


    /**
     * SkuAct构建查询对象
     *
     * @param skuAct 查询条件
     */
    public Example createExample(SkuAct skuAct) {
        Example example = new Example(SkuAct.class);
        Example.Criteria criteria = example.createCriteria();
        if (skuAct != null) {
            // 
            if (!StringUtils.isEmpty(skuAct.getSkuId())) {
                criteria.andEqualTo("skuId", skuAct.getSkuId());
            }
            // 
            if (!StringUtils.isEmpty(skuAct.getActivityId())) {
                criteria.andEqualTo("activityId", skuAct.getActivityId());
            }
        }
        return example;
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        skuActMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改SkuAct
     */
    @Override
    public void update(SkuAct skuAct) {
        skuActMapper.updateByPrimaryKey(skuAct);
    }

    /**
     * 增加SkuAct
     */
    @Override
    public void add(SkuAct skuAct) {
        //查询活动
        Activity activity = activityMapper.selectByPrimaryKey(skuAct.getActivityId());

        //修改每个Sku的信息
        for (Sku sku : skuAct.getSkus()) {
            sku.setSeckillBegin(activity.getBegintime());
            sku.setSeckillEnd(activity.getEndtime());
            sku.setStatus("2"); //1普通商品，2秒杀商品
            sku.setIslock(1);   //未锁定
            sku.setSaleNum(sku.getSeckillNum());
            sku.setAlertNum(sku.getAlertNum());
            if (sku.getSeckillBegin() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
                sku.setBgtime(simpleDateFormat.format(sku.getSeckillBegin()));
            }
            skuMapper.updateByPrimaryKeySelective(sku);

            //增加关联信息
            SkuAct newSkuAct = new SkuAct();
            newSkuAct.setActivityId(skuAct.getActivityId());
            newSkuAct.setSkuId(sku.getId());
            skuActMapper.insertSelective(newSkuAct);
        }
    }

    /**
     * 根据ID查询SkuAct
     */
    @Override
    public SkuAct findById(String id) {
        return skuActMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询SkuAct全部数据
     */
    @Override
    public List<SkuAct> findAll() {
        return skuActMapper.selectAll();
    }

    @Override
    public void deleteAll() {
        skuActMapper.delete(null);
    }

    /**
     * 增加活动下的商品
     */
    @Override
    public void addList(Activity activity, List<Sku> skuList) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(activity.getBegintime()));
        for (Sku sku : skuList) {
            SkuAct skuAct = new SkuAct();
            skuAct.setSkuId(sku.getId());
            skuAct.setActivityId(activity.getId());
            skuAct.setIsDel(1);
            skuActMapper.insertSelective(skuAct);

            //更新状态
            sku.setSeckillBegin(activity.getBegintime());
            sku.setSeckillEnd(activity.getEndtime());
            sku.setSeckillPrice(sku.getPrice() - 10);
            sku.setStatus("2");
            skuMapper.updateByPrimaryKeySelective(sku);
        }
    }

}
