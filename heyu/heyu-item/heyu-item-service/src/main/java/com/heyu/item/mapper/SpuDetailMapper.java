package com.heyu.item.mapper;

import com.heyu.item.pojo.SpuDetail;
import tk.mybatis.mapper.common.Mapper;

/*
商品详情接口
 */
public interface SpuDetailMapper extends Mapper<SpuDetail> {
    void updateByExampleSelective(SpuDetail spuDetail);
}
