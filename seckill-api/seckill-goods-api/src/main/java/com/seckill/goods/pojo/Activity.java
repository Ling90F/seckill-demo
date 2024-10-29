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
import java.util.List;

/**
 * @author http://www.itheima.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tb_activity")
public class Activity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;//

    @Column(name = "name")
    private String name;//

    @Column(name = "status")
    private Integer status;//状态：1开启，2未开启

    @Column(name = "startdate")
    private Date startdate;//

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "begintime")
    private Date begintime;//开始时间，单位：时分秒

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "endtime")
    private Date endtime;//结束时间，单位：时分秒

    @Column(name = "total_time")
    private Float totalTime;//

    @Column(name = "is_del")
    private Integer isDel;//

    //时间ID
    @Column(name = "time_id")
    private Integer timeId;

    //时间信息
    private SeckillTime seckillTime;

    //所有时间列表
    private List<SeckillTime> seckillTimeList;

    public String getStatusStr() {
        if (begintime != null && begintime.getTime() - System.currentTimeMillis() > 0) {
            return "未开始";
        }
        if (endtime != null && endtime.getTime() - System.currentTimeMillis() <= 0) {
            return "已结束";
        }
        return "进行中";
    }

}
