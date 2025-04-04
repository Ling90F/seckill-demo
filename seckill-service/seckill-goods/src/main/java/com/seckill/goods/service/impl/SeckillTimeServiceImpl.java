package com.seckill.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.dao.SeckillTimeMapper;
import com.seckill.goods.pojo.SeckillTime;
import com.seckill.goods.service.SeckillTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SeckillTimeServiceImpl implements SeckillTimeService {

    @Autowired
    private SeckillTimeMapper seckillTimeMapper;


    /**
     * SeckillTime条件+分页查询
     *
     * @param seckillTime 查询条件
     * @param page        页码
     * @param size        页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SeckillTime> findPage(SeckillTime seckillTime, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(seckillTime);
        //执行搜索
        return new PageInfo<SeckillTime>(seckillTimeMapper.selectByExample(example));
    }

    /**
     * SeckillTime分页查询
     *
     * @param page 页码
     * @param size 页大小
     */
    @Override
    public PageInfo<SeckillTime> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<SeckillTime>(seckillTimeMapper.selectAll());
    }

    /**
     * SeckillTime条件查询
     *
     * @param seckillTime 查询条件
     */
    @Override
    public List<SeckillTime> findList(SeckillTime seckillTime) {
        //构建查询条件
        Example example = createExample(seckillTime);
        //根据构建的条件查询数据
        return seckillTimeMapper.selectByExample(example);
    }


    /**
     * SeckillTime构建查询对象
     *
     * @param seckillTime 查询条件
     */
    public Example createExample(SeckillTime seckillTime) {
        Example example = new Example(SeckillTime.class);
        Example.Criteria criteria = example.createCriteria();
        if (seckillTime != null) {
            // 
            if (!StringUtils.isEmpty(seckillTime.getId())) {
                criteria.andEqualTo("id", seckillTime.getId());
            }
            // 秒杀分类名字,双十一秒杀，每日时段秒杀等
            if (!StringUtils.isEmpty(seckillTime.getName())) {
                criteria.andLike("name", "%" + seckillTime.getName() + "%");
            }
            // 开始时间
            if (!StringUtils.isEmpty(seckillTime.getStarttime())) {
                criteria.andEqualTo("starttime", seckillTime.getStarttime());
            }
            // 结束时间
            if (!StringUtils.isEmpty(seckillTime.getEndtime())) {
                criteria.andEqualTo("endtime", seckillTime.getEndtime());
            }
            // 秒杀时长,按小时计算
            if (!StringUtils.isEmpty(seckillTime.getTotalTime())) {
                criteria.andEqualTo("totalTime", seckillTime.getTotalTime());
            }
            // 状态，1：开启，2：停用
            if (!StringUtils.isEmpty(seckillTime.getStatus())) {
                criteria.andEqualTo("status", seckillTime.getStatus());
            }
        }
        return example;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Integer id) {
        seckillTimeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改SeckillTime
     */
    @Override
    public void update(SeckillTime seckillTime) {
        seckillTimeMapper.updateByPrimaryKey(seckillTime);
    }

    /**
     * 增加SeckillTime
     */
    @Override
    public void add(SeckillTime seckillTime) {
        seckillTimeMapper.insertSelective(seckillTime);
    }

    /**
     * 根据ID查询SeckillTime
     */
    @Override
    public SeckillTime findById(Integer id) {
        return seckillTimeMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询SeckillTime全部数据
     */
    @Override
    public List<SeckillTime> findAll(String name) {
        if (!StringUtils.isEmpty(name)) {
            Example example = new Example(SeckillTime.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name", "%" + name + "%");
            return seckillTimeMapper.selectByExample(example);
        }
        return seckillTimeMapper.selectAll();
    }

    /**
     * 审核
     */
    @Override
    public void audit(Integer id, Integer type) {
        SeckillTime seckillTime = new SeckillTime();
        seckillTime.setStatus(type);
        seckillTime.setId(id);
        seckillTimeMapper.updateByPrimaryKeySelective(seckillTime);
    }

    /**
     * 查询所有有效时间
     */
    @Override
    public List<SeckillTime> findAllValidTimes() {
        SeckillTime seckillTime = new SeckillTime();
        seckillTime.setStatus(1);       //已启用
        return seckillTimeMapper.select(seckillTime);
    }

    /**
     * 删除所有时间段
     */
    @Override
    public void deleteAll() {
        seckillTimeMapper.delete(null);
    }

    /**
     * 批量增加时间段
     */
    @Override
    public void addTimes(List<SeckillTime> seckillTimes) {
        for (SeckillTime seckillTime : seckillTimes) {
            seckillTimeMapper.insertSelective(seckillTime);
        }
    }

}