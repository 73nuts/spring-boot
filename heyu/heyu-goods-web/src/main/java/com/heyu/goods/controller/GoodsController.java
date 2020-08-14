package com.heyu.goods.controller;


import com.heyu.goods.service.GoodsHtmlService;
import com.heyu.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * @Description: 跳转到商品详情页
     * @Param: [id, model]
     * @return: java.lang.String
     * @Author: Big Brother
     * @Date: 2020/7/27
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model) {
        // 加载所需的数据
        Map<String, Object> map = this.goodsService.loadData(id);
        // 把数据放入数据模型
        model.addAllAttributes(map);
        // 页面静态化
        this.goodsHtmlService.creatHtml(id);
        return "item";
    }
}
