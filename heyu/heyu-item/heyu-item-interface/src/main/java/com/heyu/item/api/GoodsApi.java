package com.heyu.item.api;

import com.heyu.commin.pojo.PageResult;
import com.heyu.item.bo.SpuBo;
import com.heyu.item.pojo.Sku;
import com.heyu.item.pojo.Spu;
import com.heyu.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {
    /*
    根据条件分页查询SPU
     */
    @GetMapping("spu/page")
    public PageResult<SpuBo> queryByPage(
            @RequestParam(value = "key", required = false) String key, //查询条件
            @RequestParam(value = "saleable", required = false) Boolean saleable, //状态
            @RequestParam(value = "page", defaultValue = "1") Integer page, //默认第一页
            @RequestParam(value = "rows", defaultValue = "5") Integer rows //默认每页显示5条数据
    );

    //根据spuId查询spuDetail
    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    //根据spuId查询sku的集合
    @GetMapping("sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("id") Long spuId);

    //根据spuId查询spu
    @GetMapping("{id}")
    public Spu querySpuById(@PathVariable("id") Long id);

    @GetMapping("sku/{skuId}")
    public Sku querySkuBySkuId(@PathVariable("skuId") Long skuId);
}
