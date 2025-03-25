package com.seckill.canal.handler;

import com.seckill.goods.pojo.Sku;
import com.seckill.page.feign.SkuPageFeign;
import com.seckill.search.feign.SkuInfoFeign;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * @ClassName SkuHandler
 * @Version v1.0
 */
@Component
@CanalTable(value = "tb_sku")
public class SkuHandler implements EntryHandler<Sku> {
    @Autowired
    private SkuInfoFeign skuInfoFeign;

    @Autowired
    private SkuPageFeign skuPageFeign;

    @SneakyThrows
    @Override
    public void insert(Sku sku) {
        // 判断是否为秒杀商品再进行更新
        if (sku.getStatus().equals("2")) {
            // 新增索引库
            skuInfoFeign.modifySku(1, sku);
            // 新增静态页
            skuPageFeign.html(sku.getId());
        }
    }

    @SneakyThrows
    @Override
    public void update(Sku before, Sku after) {
        if (after.getStatus().equals("2")) {    // 判断是否为秒杀商品
            // 同步索引库
            skuInfoFeign.modifySku(2, after);
            // 同步静态页
            skuPageFeign.html(after.getId());
        } else if (before.getStatus().equals("2") && after.getStatus().equals("1")) {   // 判断是否从秒杀商品变为普通商品
            // 删除索引库
            skuInfoFeign.modifySku(3, after);
            // 删除静态页
            skuPageFeign.delHtml(after.getId());
        }

        // 逻辑删除
        if (after.getIsdel() == 2) {
            // 删除索引库
            skuInfoFeign.modifySku(3, after);
            // 删除静态页
            skuPageFeign.delHtml(after.getId());
        }
    }

    // 商品只做逻辑删除，不做物理删除，所以无需修改
    @Override
    public void delete(Sku sku) {
    }

}
