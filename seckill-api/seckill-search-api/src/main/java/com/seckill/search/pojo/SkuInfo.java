package com.seckill.search.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SkuInfo {

    // 商品id，同时也是商品编号
    // 也是ES唯一标识符，对应的_id
    private String id;

    // SKU名称
    // 需要分词
    private String name;

    //商品价格
    private Long price;

    //秒杀价
    private Long seckillPrice;

    //商品图片
    private String image;

    //更新时间
    private Date updateTime;

    //类目ID
    private String category1Id;

    //类目ID
    private String category2Id;

    //类目ID
    private String category3Id;

    //类目名称
    private String category1Name;

    //类目名称
    private String category2Name;

    //类目名称
    private String category3Name;

    //品牌名称
    private String brandName;

    //开始时间，用于做搜索
    private String bgtime;

    //品牌ID
    private String brandId;

    //秒杀开始时间
    private Date seckillBegin;

    //秒杀结束时间
    private Date seckillEnd;

    //秒杀状态，1普通商品，2秒杀
    private Integer status; 

    //规格
    private String spec;

    //比例
    private Integer points;

}