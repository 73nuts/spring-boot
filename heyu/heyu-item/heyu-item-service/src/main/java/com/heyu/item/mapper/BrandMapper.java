package com.heyu.item.mapper;

import com.heyu.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/*
品牌管理接口类
 */
public interface BrandMapper extends Mapper<Brand> {

    /*
     * 新增商品分类和品牌中间表数据
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);


    /*
     * 通过品牌 id 删除中间表
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{id}")
    void deleteCategoryAndBrandByBid(@Param("id") Long id);

    /*
    根据分类ID查找品牌列表
     */
    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);
}