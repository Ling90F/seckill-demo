package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Category;

import java.util.List;

/**
 * @author http://www.itheima.com
 */
public interface CategoryService {

    /**
     * Category多条件分页查询
     */
    PageInfo<Category> findPage(Category category, int page, int size);

    /**
     * Category分页查询
     */
    PageInfo<Category> findPage(int page, int size);

    /**
     * Category多条件搜索方法
     */
    List<Category> findList(Category category);

    /**
     * 删除Category
     */
    void delete(Integer id);

    /**
     * 修改Category数据
     */
    void update(Category category);

    /**
     * 新增Category
     */
    void add(Category category);

    /**
     * 根据ID查询Category
     */
    Category findById(Integer id);

    /**
     * 查询所有Category
     */
    List<Category> findAll();

    List<Category> findByParentId(Integer id);
}
