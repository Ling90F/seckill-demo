package com.seckill.goods.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tb_seckill_time")
public class SeckillTime implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;//

    @Column(name = "name")
    private String name;//秒杀分类名字,双十一秒杀，每日时段秒杀等

    //时间格式化
    @JsonFormat(pattern = "HH:mm")
    @Column(name = "starttime")
    private Date starttime;//开始时间

    //时间格式化
    @JsonFormat(pattern = "HH:mm")
    @Column(name = "endtime")
    private Date endtime;

    @Column(name = "total_time")
    private Float totalTime;//秒杀时长,按小时计算

    @Column(name = "status")
    private Integer status;//秒杀分类，1：启用，2：不启用

    @Column(name = "sort")
    private Integer sort;

}
