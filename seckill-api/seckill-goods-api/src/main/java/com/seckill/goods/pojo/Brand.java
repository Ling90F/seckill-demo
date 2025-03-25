package com.seckill.goods.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tb_brand")
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//品牌id

    @Column(name = "name")
    private String name;//品牌名称

    @Column(name = "image")
    private String image;//品牌图片地址

    @Column(name = "letter")
    private String letter;//品牌的首字母

    @Column(name = "seq")
    private Integer seq;//排序

}
