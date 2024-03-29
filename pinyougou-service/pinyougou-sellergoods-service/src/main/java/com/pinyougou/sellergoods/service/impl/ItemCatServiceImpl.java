package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类接口实现类
 * @author 27847
 * @version 1.0
 * @date 2019/03/08 22:20
 **/
@Service(interfaceName = "com.pinyougou.service.ItemCatService")
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 添加方法
     *
     * @param itemCat
     */
    @Override
    public void save(ItemCat itemCat) {

    }

    /**
     * 修改方法
     *
     * @param itemCat
     */
    @Override
    public void update(ItemCat itemCat) {

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
    public ItemCat findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<ItemCat> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param itemCat
     * @param page
     * @param rows
     */
    @Override
    public List<ItemCat> findByPage(ItemCat itemCat, int page, int rows) {
        return null;
    }

    /**
     * 根据父级id查找商品分类
     *
     * @param parentId
     */
    @Override
    public List<ItemCat> findItemCatByParentId(Long parentId) {
        try {
            return itemCatMapper.findItemCatByParentId(parentId);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
