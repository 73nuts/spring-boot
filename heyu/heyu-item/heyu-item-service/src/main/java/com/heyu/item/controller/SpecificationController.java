package com.heyu.item.controller;

import com.heyu.item.pojo.SpecGroup;
import com.heyu.item.pojo.SpecParam;
import com.heyu.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
参数规格实现类
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * @Description: 根据分类id查询分组
     * @Param: [cid]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.SpecGroup>>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid") Long cid) {
        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * @Description: 更新规格组
     * @Param: [specGroup]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateGroups(@RequestBody SpecGroup specGroup) {
        this.specificationService.updateGroups(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Description: 新增规格组
     * @Param: [specGroup]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @PostMapping("group")
    public ResponseEntity<Void> insertGroups(@RequestBody SpecGroup specGroup) {
        this.specificationService.insertGroups(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Description: 删除规格组
     * @Param: [id]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroups(@PathVariable("id") Long id) {
        this.specificationService.deleteGroups(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * @Description: 根据条件查询规格参数
     * @Param: [gid, cid, generic, searching]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.SpecParam>>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    ) {

        List<SpecParam> params = this.specificationService.queryParams(gid, cid, generic, searching);

        if (CollectionUtils.isEmpty(params)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }


    /**
     * @Description: 更新规格参数
     * @Param: [specParam]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateParams(@RequestBody SpecParam specParam) {
        this.specificationService.updateParams(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * @Description: 新增规格组
     * @Param: [specParam]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @PostMapping("param")
    public ResponseEntity<Void> insertParams(@RequestBody SpecParam specParam) {
        this.specificationService.insertParams(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Description: 删除规格组
     * @Param: [id]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParams(@PathVariable("id") Long id) {
        this.specificationService.deleteParams(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * @Description: 根据分类Id查询所有规格参数集合
     * @Param: [cid]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.SpecGroup>>
     * @Author: Big Brother
     * @Date: 2020/7/27
     */
    @GetMapping("group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupWithParam(@PathVariable("cid") Long cid) {
        List<SpecGroup> groups = this.specificationService.queryGroupWithParam(cid);
        if (CollectionUtils.isEmpty(groups)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

}