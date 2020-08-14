package com.heyu.item.api;


import com.heyu.item.pojo.Brand;
import org.springframework.web.bind.annotation.*;



/*
品牌管理实现类
 */

@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("{id}")
    public Brand queryBrandsById(@PathVariable("id") Long id);

}