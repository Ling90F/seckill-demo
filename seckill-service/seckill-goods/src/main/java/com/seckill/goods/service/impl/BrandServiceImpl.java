package com.seckill.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.dao.BrandMapper;
import com.seckill.goods.pojo.Brand;
import com.seckill.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * Brand条件+分页查询
     *
     * @param brand 查询条件
     * @param page  页码
     * @param size  页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(brand);
        //执行搜索
        return new PageInfo<>(brandMapper.selectByExample(example));
    }

    /**
     * Brand分页查询
     *
     * @param page 页码
     * @param size 页大小
     */
    @Override
    public PageInfo<Brand> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<>(brandMapper.selectAll());
    }

    /**
     * Brand条件查询
     *
     * @param brand 查询条件
     */
    @Override
    public List<Brand> findList(Brand brand) {
        //构建查询条件
        Example example = createExample(brand);
        //根据构建的条件查询数据
        return brandMapper.selectByExample(example);
    }


    /**
     * Brand构建查询对象
     *
     * @param brand 查询条件
     */
    public Example createExample(Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (brand != null) {
            // 品牌id
            if (!StringUtils.isEmpty(brand.getId())) {
                criteria.andEqualTo("id", brand.getId());
            }
            // 品牌名称
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            // 品牌图片地址
            if (!StringUtils.isEmpty(brand.getImage())) {
                criteria.andEqualTo("image", brand.getImage());
            }
            // 品牌的首字母
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
            // 排序
            if (!StringUtils.isEmpty(brand.getSeq())) {
                criteria.andEqualTo("seq", brand.getSeq());
            }
        }
        return example;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 增加Brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 根据ID查询Brand
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Brand全部数据
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }
}
