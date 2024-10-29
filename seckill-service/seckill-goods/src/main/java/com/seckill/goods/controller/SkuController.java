package com.seckill.goods.controller;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.service.SkuService;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author http://www.itheima.com
 */
@RestController
@RequestMapping("/sku")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;


    /**
     * 删除指定商品
     */
    @DeleteMapping(value = "/del/{id}")
    public Result<Sku> del(@PathVariable(value = "id") String id) {
        skuService.delete(id);
        return new Result<>(true, StatusCode.OK, "删除成功！");
    }

    /**
     * 指定活动下的秒杀商品
     */
    @GetMapping(value = "/activity/{id}/{page}/{size}")
    public Result<PageInfo<Sku>> actList(@PathVariable(value = "id") String id,
                                         @PathVariable(value = "page") Integer page,
                                         @PathVariable(value = "size") Integer size) {
        PageInfo<Sku> pageInfo = skuService.findSkuByActivityId(id, page, size);
        return new Result<>(true, StatusCode.OK, "查询成功！", pageInfo);
    }

    /**
     * 锁定商品
     */
    @GetMapping(value = "/lock/{id}")
    public Result lock(@PathVariable(value = "id") String id) {
        //锁定商品
        skuService.lock(id);
        return new Result(true, StatusCode.OK, "锁定商品成功");
    }


    /**
     * 解锁商品
     */
    @GetMapping(value = "/unlock/{id}")
    public Result unlock(@PathVariable(value = "id") String id) {
        //解锁商品
        skuService.unlock(id);
        return new Result(true, StatusCode.OK, "解锁商品成功");
    }

    /**
     * Sku分页条件搜索实现
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Sku sku, @PathVariable int page, @PathVariable int size) {
        // sku.setName(sku.getName().replace("  "," "));
        //调用SkuService实现分页条件查询Sku
        sku.setStatus("1"); //普通商品
        PageInfo<Sku> pageInfo = skuService.findPage(sku, page, size);
        return new Result<>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /**
     * Sku分页搜索实现
     *
     * @param page:当前页
     * @param size:每页显示多少条
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用SkuService实现分页查询Sku
        PageInfo<Sku> pageInfo = skuService.findPage(page, size);
        return new Result<>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     */
    @PostMapping(value = "/search")
    public Result<List<Sku>> findList(@RequestBody(required = false) Sku sku) {
        //调用SkuService实现条件查询Sku
        List<Sku> list = skuService.findList(sku);
        return new Result<>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据ID删除品牌数据
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        //调用SkuService实现根据主键删除
        skuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改Sku数据
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Sku sku, @PathVariable String id) {
        //时间操作
        //Date beginTime =new Date( sku.getStartDate().getTime()+ TimeUtil.dateFormatHHmm(sku.getStartTimestr()).getTime() );
        //sku.setSeckillBegin(beginTime);

        //N 小时之后，计算活动结束时间
        //Date endTime = TimeUtil.addDateHour(beginTime,sku.getLen());
        //sku.setSeckillEnd(endTime);

        //设置主键值
        sku.setId(id);
        //调用SkuService实现修改Sku
        skuService.update(sku);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 新增Sku数据
     */
    @PostMapping
    public Result add(@RequestBody Sku sku) {
        //调用SkuService实现添加Sku
        skuService.add(sku);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据ID查询Sku数据
     */
    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable String id) {
        //调用SkuService实现根据主键查询Sku
        Sku sku = skuService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", sku);
    }

    /**
     * 查询Sku全部数据
     */
    @GetMapping
    public Result<List<Sku>> findAll() {
        //调用SkuService实现查询所有Sku
        List<Sku> list = skuService.findAll();
        return new Result<>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 商品数据归0
     */
    @GetMapping(value = "/sku/zero/{id}")
    public Result zero(@PathVariable(value = "id") String id) {
        skuService.zero(id);
        return new Result(true, StatusCode.OK, "数据归零成功");
    }

    /**
     * 根据ID查询4条记录
     */
    @GetMapping(value = "/act/{id}")
    public Result<List<Sku>> findTop(@PathVariable(value = "id") String id) {
        List<Sku> skus = skuService.findTop(id);
        return new Result<>(true, StatusCode.OK, "查询成功", skus);
    }
}



