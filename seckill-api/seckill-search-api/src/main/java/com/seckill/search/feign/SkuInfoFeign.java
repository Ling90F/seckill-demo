package com.seckill.search.feign;

import com.seckill.goods.pojo.Sku;
import com.seckill.search.pojo.SkuInfo;
import com.seckill.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "seckill-search")
public interface SkuInfoFeign {

    /**
     * 将一条记录导入到搜索引擎中
     */
    @PostMapping(value = "/search/modify/{type}")
    Result modify(@PathVariable(value = "type") Integer type, @RequestBody SkuInfo skuInfo);

    /**
     * 修改Sku
     */
    @PostMapping(value = "/search/modify/sku/{type}")
    Result modifySku(@PathVariable(value = "type") Integer type, @RequestBody Sku sku);
}