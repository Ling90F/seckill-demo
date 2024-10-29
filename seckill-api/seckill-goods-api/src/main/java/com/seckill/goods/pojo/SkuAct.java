package com.seckill.goods.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author http://www.itheima.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tb_sku_act")
public class SkuAct implements Serializable {

    @Id
    @Column(name = "sku_id")
    private String skuId;//

    @Column(name = "activity_id")
    private String activityId;//

    //是否删除
    @Column(name = "is_del")
    private Integer isDel;

    //Sku集合
    private List<Sku> skus;

}
