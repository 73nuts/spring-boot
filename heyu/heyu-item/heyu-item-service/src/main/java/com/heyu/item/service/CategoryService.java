package com.heyu.item.service;

import com.heyu.item.mapper.CategoryMapper;
import com.heyu.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
分类查询服务类
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * @Description: 根据父节点查询子节点
     * @Param: [pid]
     * @return: java.util.List<com.heyu.item.pojo.Category>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    /**
     * @Description: 根据品牌 Id 查询品牌分类
     * @Param: [bid]
     * @return: java.util.List<com.heyu.item.pojo.Category>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    public List<Category> queryCategoryByBrandId(Long bid) {
        List<Category> categories = categoryMapper.queryCategoryByBrandId(bid);
        return categories;
    }

    /**
     * @Description: 根据 Id 查询品牌分类
     * @Param: [ids]
     * @return: java.util.List<java.lang.String>
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    public List<String> queryCategoryByIds(List<Long> ids) {
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }

    /**
     * @Description: 根据3级分类id，查询1~3级的分类
     * @Param: [id]
     * @return: java.util.List<com.heyu.item.pojo.Category>
     * @Author: Big Brother
     * @Date: 2020/7/26
     */
    public List<Category> queryAllByCid3(Long id) {
        Category c3 = this.categoryMapper.selectByPrimaryKey(id);
        Category c2 = this.categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = this.categoryMapper.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1, c2, c3);
    }
}
