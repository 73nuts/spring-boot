package com.heyu.item.controller;

import com.heyu.item.pojo.Category;
import com.heyu.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
分类查询实现类
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * @Description: 根据父节点的ID查询子节点
     * @Param: [pid]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.Category>>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        try {
            if (pid == null || pid < 0) {
                //400:参数不合法
                return ResponseEntity.badRequest().build();
            }
            List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
            if (CollectionUtils.isEmpty(categories)) {
                //404:资源服务器未找到
                return ResponseEntity.notFound().build();
            }
            //200：查询成功
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //500:服务器内部错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * @Description: 根据品牌 Id 查询品牌分类
     * @Param: [bid]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.Category>>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBrandId(@PathVariable("bid") Long bid) {
        if (bid == null || bid.longValue() < 0) {
            return ResponseEntity.badRequest().build(); // 响应 400
        }
        List<Category> categories = categoryService.queryCategoryByBrandId(bid);
        if (CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.notFound().build(); // 响应 404
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * @Description: 根据 Id 查询品牌分类
     * @Param: [ids]
     * @return: org.springframework.http.ResponseEntity<java.util.List < java.lang.String>>
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryCategoryByIds(@RequestParam("ids") List<Long> ids) {

        List<String> names = this.categoryService.queryCategoryByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    /**
     * @Description: 根据3级分类id，查询1~3级的分类
     * @Param: [id]
     * @return: org.springframework.http.ResponseEntity<java.util.List < com.heyu.item.pojo.Category>>
     * @Author: Big Brother
     * @Date: 2020/7/26
     */
    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long id) {
        List<Category> list = this.categoryService.queryAllByCid3(id);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
