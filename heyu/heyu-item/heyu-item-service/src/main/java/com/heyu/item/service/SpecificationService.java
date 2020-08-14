package com.heyu.item.service;

import com.heyu.item.mapper.SpecGroupMapper;
import com.heyu.item.mapper.SpecParamMapper;
import com.heyu.item.pojo.SpecGroup;
import com.heyu.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
规格参数服务类
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;


    /**
     * @Description: 根据分类id查询分组
     * @Param: [cid]
     * @return: java.util.List<com.heyu.item.pojo.SpecGroup>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.groupMapper.select(specGroup);
    }


    /**
     * @Description: 修改规格参数组
     * @Param: [specGroup]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    @Transactional
    public void updateGroups(SpecGroup specGroup) {
        this.groupMapper.updateByPrimaryKey(specGroup);
    }

    /**
     * @Description: 添加规格参数组
     * @Param: [specGroup]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    @Transactional
    public void insertGroups(SpecGroup specGroup) {
        this.groupMapper.insertSelective(specGroup);
    }


    /**
     * @Description: 删除规格参数组
     * @Param: [id]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    @Transactional
    public void deleteGroups(Long id) {
        this.groupMapper.deleteByPrimaryKey(id);
    }


    /**
     * @Description: 根据条件查询规格参数
     * @Param: [gid, cid, generic, searching]
     * @return: java.util.List<com.heyu.item.pojo.SpecParam>
     * @Author: Big Brother
     * @Date: 2020/7/16
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.paramMapper.select(record);
    }

    /**
     * @Description: 修改规格参数
     * @Param: [specParam]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/19
     */
    @Transactional
    public void updateParams(SpecParam specParam) {
        this.paramMapper.updateByPrimaryKey(specParam);
    }


    /**
     * @Description: 添加规格参数
     * @Param: [specParam]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/19
     */
    @Transactional
    public void insertParams(SpecParam specParam) {
        this.paramMapper.insertSelective(specParam);
    }


    /**
     * @Description: 删除规格参数
     * @Param: [id]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/19
     */
    @Transactional
    public void deleteParams(Long id) {
        this.paramMapper.deleteByPrimaryKey(id);
    }


    /**
     * @Description: 根据分类Id查询所有规格参数集合
     * @Param: [cid]
     * @return: java.util.List<com.heyu.item.pojo.SpecGroup>
     * @Author: Big Brother
     * @Date: 2020/7/27
     */
    public List<SpecGroup> queryGroupWithParam(Long cid) {
        List<SpecGroup> groups = this.queryGroupsByCid(cid);
        groups.forEach(group -> {
            List<SpecParam> params = this.queryParams(group.getId(), null, null, null);
            group.setParams(params);
        });
        return groups;
    }
}