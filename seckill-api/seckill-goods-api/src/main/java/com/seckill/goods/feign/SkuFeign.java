package com.seckill.goods.feign;

import com.seckill.goods.pojo.Sku;
import com.seckill.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author http://www.itheima.com
 */
@FeignClient(value = "seckill-goods")
public interface SkuFeign {

    /**
     * 锁定商品
     */
    @GetMapping(value = "/sku/lock/{id}")
    Result lock(@PathVariable(value = "id") String id);

    /**
     * 解锁商品
     */
    @GetMapping(value = "/sku/unlock/{id}")
    Result unlock(@PathVariable(value = "id") String id);

    /**
     * 根据ID查询Sku数据
     */
    @GetMapping("/sku/{id}")
    Result<Sku> findById(@PathVariable String id);

    /**
     * 商品数据归0
     */
    @GetMapping(value = "/sku/zero/{id}")
    Result zero(@PathVariable(value = "id") String id);
}
