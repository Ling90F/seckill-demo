package com.seckill.goods.controller;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.SeckillTime;
import com.seckill.goods.service.SeckillTimeService;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import com.seckill.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seckillTime")
@CrossOrigin
public class SeckillTimeController {

    @Autowired
    private SeckillTimeService seckillTimeService;

    /**
     * SeckillTime分页条件搜索实现
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  SeckillTime seckillTime, @PathVariable  int page, @PathVariable  int size){
        //调用SeckillTimeService实现分页条件查询SeckillTime
        PageInfo<SeckillTime> pageInfo = seckillTimeService.findPage(seckillTime, page, size);
        return new Result<>(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /**
     * SeckillTime分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用SeckillTimeService实现分页查询SeckillTime
        PageInfo<SeckillTime> pageInfo = seckillTimeService.findPage(page, size);
        return new Result<>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     */
    @PostMapping(value = "/search" )
    public Result<List<SeckillTime>> findList(@RequestBody(required = false)  SeckillTime seckillTime){
        //调用SeckillTimeService实现条件查询SeckillTime
        List<SeckillTime> list = seckillTimeService.findList(seckillTime);
        return new Result<>(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 根据ID删除品牌数据
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用SeckillTimeService实现根据主键删除
        seckillTimeService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 修改SeckillTime数据
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  SeckillTime seckillTime,@PathVariable Integer id){
        //设置主键值
        seckillTime.setId(id);
        //时间更新成当前时间以及计算时间差值
        Date[] dates = TimeUtil.twoHours(seckillTime.getStarttime(), seckillTime.getEndtime());
        seckillTime.setStarttime(dates[0]);
        seckillTime.setEndtime(dates[1]);
        seckillTime.setTotalTime(TimeUtil.dif2hour(seckillTime.getStarttime(),seckillTime.getEndtime()));

        //调用SeckillTimeService实现修改SeckillTime
        seckillTimeService.update(seckillTime);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 新增SeckillTime数据
     */
    @PostMapping
    public Result add(@RequestBody   SeckillTime seckillTime){
        //时间更新成当前时间以及计算时间差值
        Date[] dates = TimeUtil.twoHours(seckillTime.getStarttime(), seckillTime.getEndtime());
        seckillTime.setStarttime(dates[0]);
        seckillTime.setEndtime(dates[1]);
        seckillTime.setTotalTime(TimeUtil.dif2hour(seckillTime.getStarttime(),seckillTime.getEndtime()));
        //调用SeckillTimeService实现添加SeckillTime
        seckillTimeService.add(seckillTime);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 根据ID查询SeckillTime数据
     */
    @GetMapping("/{id}")
    public Result<SeckillTime> findById(@PathVariable Integer id){
        //调用SeckillTimeService实现根据主键查询SeckillTime
        SeckillTime seckillTime = seckillTimeService.findById(id);
        return new Result<>(true,StatusCode.OK,"查询成功",seckillTime);
    }

    /**
     * 查询SeckillTime全部数据
     */
    @GetMapping
    public Result<List<SeckillTime>> findAll(@RequestParam(value = "searchName")String searchName){
        //调用SeckillTimeService实现查询所有SeckillTime
        List<SeckillTime> list = seckillTimeService.findAll(searchName);
        return new Result<>(true, StatusCode.OK,"查询成功",list) ;
    }

    /**
     * 查询SeckillTime全部数据
     */
    @GetMapping(value = "/valid")
    public Result<List<SeckillTime>> findValidAll(){
        //调用SeckillTimeService实现查询所有SeckillTime
        List<SeckillTime> list = seckillTimeService.findAllValidTimes();
        return new Result<>(true, StatusCode.OK,"查询成功",list) ;
    }

    /**
     * 启用/不启用
     */
    @PutMapping(value="/audit/{id}/{type}")
    public Result audit(@PathVariable(value = "id")Integer id,@PathVariable(value = "type")Integer type){
        //审核操作
        seckillTimeService.audit(id,type);
        return new Result(true,StatusCode.OK,"审核成功");
    }
}
