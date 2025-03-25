package com.seckill.page.service;

import java.util.Map;

public interface SkuPageService {

    /**
     * 删除静态页
     * @param id
     * @param htmlPath
     */
    void delItemPage(String id,String htmlPath);

    /**
     * 生成静态页
     */
    void writePage(Map<String,Object> dataMap) throws Exception;
}