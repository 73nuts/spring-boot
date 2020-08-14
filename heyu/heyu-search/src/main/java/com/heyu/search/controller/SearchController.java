package com.heyu.search.controller;

import com.heyu.commin.pojo.PageResult;
import com.heyu.item.pojo.Category;
import com.heyu.search.pojo.Goods;
import com.heyu.search.pojo.SearchRequest;
import com.heyu.search.pojo.SearchResult;
import com.heyu.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
搜索实现类
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;


    /**
     * @Description: 返回查询结果集
     * @Param: [request]
     * @return: org.springframework.http.ResponseEntity<com.heyu.search.pojo.SearchResult>
     * @Author: Big Brother
     * @Date: 2020/7/22
     */
    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest request) {
        SearchResult result = this.searchService.search(request);
        if (request == null || CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }


}
