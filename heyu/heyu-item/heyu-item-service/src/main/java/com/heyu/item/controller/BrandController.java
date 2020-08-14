package com.heyu.item.controller;

import com.heyu.commin.pojo.PageResult;
import com.heyu.item.pojo.Brand;
import com.heyu.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
品牌管理实现类
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * @Description: 根据查询条件分页并排序查询品牌信息
     * @Param: [key, page, rows, sortBy, desc]
     * @return: org.springframework.http.ResponseEntity<com.heyu.commin.pojo.PageResult < com.heyu.item.pojo.Brand>>
     * @Author: Big Brother
     * @Date: 2020/7/14
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,//搜索条件
            @RequestParam(value = "page", defaultValue = "1") Integer page,//当前页(默认为1)
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,//每页显示的搜索结果(默认5条)
            @RequestParam(value = "sortBy", required = false) String sortBy,//排序字段
            @RequestParam(value = "desc", required = false) Boolean desc //排序方式
    ) {
        //返回查询结果
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        //判断查询内容是否为空
        if (CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * @Description: 新增品牌
     * @Param: [brand, cids]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/15
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        this.brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * @Description: 更新品牌
     * @Param: [brand, cids]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/15
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        this.brandService.updateBrand(cids, brand);
        // 响应 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * @Description: 删除品牌
     * @Param: [bid]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/15
     */
    @DeleteMapping("/bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") Long bid) {
        brandService.deleteBrand(bid);
        // 响应 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * @Description: 根据分类ID查找品牌列表
     * @Param: [cid]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.Brand>>
     * @Author: Big Brother
     * @Date: 2020/7/14
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid) {
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

    /**
     * @Description: 根据品牌Id查找品牌
     * @Param: [id]
     * @return: org.springframework.http.ResponseEntity<com.heyu.item.pojo.Brand>
     * @Author: Big Brother
     * @Date: 2020/7/14
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandsById(@PathVariable("id") Long id) {
        Brand brand = this.brandService.queryBrandsById(id);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }
}