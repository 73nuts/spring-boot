package com.heyu.item.api;


import com.heyu.item.pojo.SpecGroup;
import com.heyu.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
参数规格实现类
 */
@RequestMapping("spec")
public interface SpecificationApi {

    //根据条件查询规格参数
    @GetMapping("params")
    public List<SpecParam> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    //根据分类Id查询所有规格参数集合
    @GetMapping("group/param/{cid}")
    public List<SpecGroup> queryGroupWithParam(@PathVariable("cid") Long cid);
}