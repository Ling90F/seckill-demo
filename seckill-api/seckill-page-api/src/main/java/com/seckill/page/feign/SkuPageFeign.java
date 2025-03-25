package com.seckill.page.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.seckill.util.Result;

@FeignClient(value = "seckill-page")
public interface SkuPageFeign {
    /**
     * 删除商品详情静态页
     */
    @DeleteMapping(value = "/page/html/{id}")
    Result delHtml(@PathVariable(value = "id")String id) throws Exception;
    
    /**
     * 静态页生成
     */
    @GetMapping(value = "/page/html/{id}")
    Result html(@PathVariable(value = "id") String id) throws Exception;
}