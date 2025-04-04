package com.seckill.goods.dao;

import com.seckill.goods.pojo.Activity;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ActivityMapper extends Mapper<Activity> {

    /**
     * 时间查询
     */
    @Select("(SELECT * FROM tb_activity WHERE begintime<NOW() AND `status`=1 ORDER BY begintime DESC LIMIT 1) UNION (SELECT * FROM tb_activity WHERE begintime>=NOW() AND `status`=1 ORDER BY begintime ASC)  LIMIT 5")
    List<Activity> times();
}
