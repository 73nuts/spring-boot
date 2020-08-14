package com.heyu.item.mapper;

import com.heyu.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/*
分类接口
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category, Long> {

    /*
     * 根据品牌 Id 查询品牌分类
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryCategoryByBrandId(Long bid);
}
