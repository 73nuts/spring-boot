package com.heyu.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heyu.commin.pojo.PageResult;
import com.heyu.item.bo.SpuBo;
import com.heyu.item.mapper.*;
import com.heyu.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*
商品管理服务类
 */
@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;


    @Autowired
    private BrandMapper brandMapper;


    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * @Description: 根据条件分页查询SPU
     * @Param: [key, saleable, page, rows]
     * @return: com.heyu.commin.pojo.PageResult<com.heyu.item.bo.SpuBo>
     * @Author: Big Brother
     * @Date: 2020/7/26
     */
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        //添加查询条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        //添加上下架的过滤条件
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        //添加分页
        PageHelper.startPage(page, rows);

        //执行查询，获取SPU集合
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);

        //将SPU集合转换为SPUBO集合
        List<SpuBo> spuBos = spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);

            //查询品牌名称
            Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());

            //查询分类名称
            List<String> names = this.categoryService.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "-"));
            return spuBo;
        }).collect(Collectors.toList());

        //返回pageResult
        return new PageResult<>(pageInfo.getTotal(), spuBos);
    }


    /**
     * @Description: 新增商品
     * @Param: [spuBo]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/18
     */
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        // 新增spu
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);

        //新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuBo.getSpuDetail());


        saveSkuAndStock(spuBo);

        sendMsg("insert", spuBo.getId());
    }

    /**
     * @Description: 发送消息
     * @Param: [type, id]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    private void sendMsg(String type, Long id) {
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Description: 新增sku和stock
     * @Param: [spuBo]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/19
     */
    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            //新增sku
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);

            //新增stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }


    /**
     * @Description: 编辑商品
     * @Param: [spuId]
     * @return: com.heyu.item.pojo.SpuDetail
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    //根据spuId查询spuDetail
    public SpuDetail querySpuDetailBySpuId(Long spuId) {

        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    //根据spuId查询sku的集合
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        skus.forEach(s -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(s.getId());
            s.setStock(stock.getStock());
        });
        return skus;
    }

    /**
     * @Description: 更新商品
     * @Param: [spuBo]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/17
     */
    @Transactional
    public void updateGoods(SpuBo spuBo) {
        // 获取要删除的 Sku
        Sku record = new Sku();
        record.setSpuId(spuBo.getId());
        List<Sku> skus = skuMapper.select(record);
        skus.forEach(sku -> {
            //删除stock
            this.stockMapper.deleteByPrimaryKey(sku.getId());
        });


        //删除sku
        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        this.skuMapper.delete(sku);

        // 新增 Sku 和 Stock
        this.saveSkuAndStock(spuBo);

        // 更新 Spu
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(new Date());
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);

        // 更新 SpuDetail
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());

        sendMsg("update", spuBo.getId());
    }


    /**
     * @Description: 根据spu的id查询spu
     * @Param: [id]
     * @return: com.heyu.item.pojo.Spu
     * @Author: Big Brother
     * @Date: 2020/7/27
     */
    public Spu querySpuById(Long id) {
        return this.spuMapper.selectByPrimaryKey(id);
    }

    /**
     * @Description: 根据SkuId查询Sku
     * @Param: [skuId]
     * @return: com.heyu.item.pojo.Sku
     * @Author: Big Brother
     * @Date: 2020/7/30
     */
    public Sku querySkuBySkuId(Long skuId) {
        return this.skuMapper.selectByPrimaryKey(skuId);
    }
}
