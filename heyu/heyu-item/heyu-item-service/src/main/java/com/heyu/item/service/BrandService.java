package com.heyu.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heyu.commin.pojo.PageResult;
import com.heyu.item.mapper.BrandMapper;
import com.heyu.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/*
品牌管理服务类
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * @Description: 根据查询条件分页并排序查询品牌信息
     * @Param: [key, page, rows, sortBy, desc]
     * @return: com.heyu.commin.pojo.PageResult<com.heyu.item.pojo.Brand>
     * @Author: Big Brother
     * @Date: 2020/7/14
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //根据品牌名称(name)或者首字母进行模糊查询
        //判断查询条件是否不为空且长度不为0且不由空白符构成
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        // 添加分页条件
        PageHelper.startPage(page, rows);

        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);
        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回(获取总条数，获取记录信息)
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * @Description: 新增品牌
     * @Param: [brand, cids]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/15
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        // 先新增brand
        this.brandMapper.insertSelective(brand);

        // 再新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertBrandAndCategory(cid, brand.getId());
        });
    }


    /**
     * @Description: 更新品牌
     * @Param: [cids, brand]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/15
     */
    @Transactional
    public void updateBrand(List<Long> cids, Brand brand) {
        // 先更新 Brand
        brandMapper.updateByPrimaryKey(brand);
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(brand.getId());
        // 再新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertBrandAndCategory(cid, brand.getId());
        });
    }

    /**
     * @Description: 删除品牌
     * @Param: [bid]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/15
     */
    @Transactional
    public void deleteBrand(Long bid) {
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(bid);
        // 删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }


    /**
     * @Description: 根据分类ID查找品牌列表
     * @Param: [cid]
     * @return: java.util.List<com.heyu.item.pojo.Brand>
     * @Author: Big Brother
     * @Date: 2020/7/15
     */
    public List<Brand> queryBrandsByCid(Long cid) {
        return this.brandMapper.selectBrandByCid(cid);
    }


    public Brand queryBrandsById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }
}
