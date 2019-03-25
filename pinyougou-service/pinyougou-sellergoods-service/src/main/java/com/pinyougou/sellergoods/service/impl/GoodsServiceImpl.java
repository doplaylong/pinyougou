package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.mapper.GoodsMapper;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.GoodsDesc;
import com.pinyougou.pojo.Item;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/03/16 16:50
 **/
@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 添加方法
     *
     * @param goods
     */
    @Override
    public void save(Goods goods) {

    }

    /**
     * 修改方法
     *
     * @param goods
     */
    @Override
    public void update(Goods goods) {

    }

    /**
     * 根据主键id删除
     *
     * @param id
     */
    @Override
    public void delete(Serializable id) {

    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteAll(Serializable[] ids) {

    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Goods findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Goods> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param goods
     * @param page
     * @param rows
     */
    @Override
    public List<Goods> findByPage(Goods goods, int page, int rows) {
        return null;
    }

    /**
     * 根据商品id查询商品信息
     *
     * @param goodsId
     */
    @Override
    public Map<String, Object> getGoods(Long goodsId) {
        try {
            /* 定义数据模型 */
            Map<String,Object> dataModel = new HashMap<>();
            /* 加载商品spu数据 */
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods", goods);
            /* 加载商品描述数据 */
            GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc", goodsDesc);
            /* 加载商品sku数据 */
            Example example = new Example(Item.class);
            /* 查询条件对象 */
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("goodsId", goodsId);
            criteria.andEqualTo("status", "1");
            example.orderBy("isDefault").desc();
            /*根据条件查询sku商品数据*/
            List<Item> itemList = itemMapper.selectByExample(example);
            dataModel.put("itemList", JSON.toJSONString(itemList));
            /* 商品分类 */
            if (goods != null && goods.getCategory3Id() != null) {
                String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
                String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
                String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
                dataModel.put("itemCat1", itemCat1);
                dataModel.put("itemCat2", itemCat2);
                dataModel.put("itemCat3", itemCat3);
            }
            return dataModel;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
