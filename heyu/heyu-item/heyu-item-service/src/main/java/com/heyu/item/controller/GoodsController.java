package com.heyu.item.controller;

import com.heyu.commin.pojo.PageResult;
import com.heyu.item.bo.SpuBo;
import com.heyu.item.pojo.Sku;
import com.heyu.item.pojo.Spu;
import com.heyu.item.pojo.SpuDetail;
import com.heyu.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
商品管理实现类
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    /**
     * @Description: 根据条件分页查询SPU
     * @Param: [key, saleable, page, rows]
     * @return: org.springframework.http.ResponseEntity<com.heyu.commin.pojo.PageResult < com.heyu.item.bo.SpuBo>>
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> queryByPage(
            @RequestParam(value = "key", required = false) String key, //查询条件
            @RequestParam(value = "saleable", required = false) Boolean saleable, //状态
            @RequestParam(value = "page", defaultValue = "1") Integer page, //默认第一页
            @RequestParam(value = "rows", defaultValue = "5") Integer rows //默认每页显示5条数据
    ) {
        PageResult<SpuBo> result = this.goodsService.querySpuByPage(key, saleable, page, rows);

        if (result == null || CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * @Description: 新增商品
     * @Param: [spuBo]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo) {  //通过@RequestBody注解来接收Json请求
        this.goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * @Description: 根据spuId查询spuDetail
     * @Param: [spuId]
     * @return: org.springframework.http.ResponseEntity<com.heyu.item.pojo.SpuDetail>
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }


    /**
     * @Description: 根据spuId查询sku的集合
     * @Param: [spuId]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.Sku>>
     * @Author: Big Brother
     * @Date: 2020/7/18
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * @Description: 修改商品信息
     * @Param: [spuBo]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/18
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo) {
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @Description: 根据spuId查询spu
     * @Param: [id]
     * @return: org.springframework.http.ResponseEntity<com.heyu.item.pojo.Spu>
     * @Author: Big Brother
     * @Date: 2020/7/27
     */
    @GetMapping("{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id) {
        Spu spu = this.goodsService.querySpuById(id);
        if (spu == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spu);
    }

    /**
     * @Description: 根据SkuId查询Sku
     * @Param: [skuId]
     * @return: org.springframework.http.ResponseEntity<com.heyu.item.pojo.Sku>
     * @Author: Big Brother
     * @Date: 2020/7/30
     */
    @GetMapping("sku/{skuId}")
    public ResponseEntity<Sku> querySkuBySkuId(@PathVariable("skuId") Long skuId) {
        Sku sku = this.goodsService.querySkuBySkuId(skuId);
        if (sku == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sku);
    }
}
