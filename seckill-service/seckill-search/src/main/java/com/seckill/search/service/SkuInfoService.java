package com.seckill.search.service;

import com.seckill.search.pojo.SkuInfo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface SkuInfoService {

    /**
     * 搜索
     */
    Page<SkuInfo> search(Map<String,String> searchMap);
    
    /**
     * 批量导入
     */
    void addAll();

    /**
     * 单条索引操作
     * @param type: 1:增加，2：修改，3：删除
     * @param skuInfo
     */
    void modify(Integer type, SkuInfo skuInfo);
}