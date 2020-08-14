package com.heyu.item.bo;

import com.heyu.item.pojo.Sku;
import com.heyu.item.pojo.Spu;
import com.heyu.item.pojo.SpuDetail;
import com.sun.scenario.effect.impl.prism.PrImage;

import java.util.List;

/*
为完成商品查询所创建的类，没有实体对象
 */
public class SpuBo extends Spu {

    String cname;// 商品分类名称
    String bname;// 品牌名称
    SpuDetail spuDetail;// 商品详情
    List<Sku> skus;// sku列表

    //get和set方法
    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
