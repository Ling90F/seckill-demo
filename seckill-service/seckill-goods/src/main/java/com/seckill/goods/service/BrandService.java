package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Brand;

import java.util.List;

/**
 * @author http://www.itheima.com
 */
public interface BrandService {

    /**
     * Brand多条件分页查询
     */
    PageInfo<Brand> findPage(Brand brand, int page, int size);

    /**
     * Brand分页查询
     */
    PageInfo<Brand> findPage(int page, int size);

    /**
     * Brand多条件搜索方法
     */
    List<Brand> findList(Brand brand);

    /**
     * 删除Brand
     */
    void delete(Integer id);

    /**
     * 修改Brand数据
     */
    void update(Brand brand);

    /**
     * 新增Brand
     */
    void add(Brand brand);

    /**
     * 根据ID查询Brand
     */
    Brand findById(Integer id);

    /**
     * 查询所有Brand
     */
    List<Brand> findAll();
}
