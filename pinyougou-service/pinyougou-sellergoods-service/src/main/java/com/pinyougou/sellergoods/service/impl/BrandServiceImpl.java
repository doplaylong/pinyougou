package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.pojo.PageResult;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 品牌服务接口实现类
 *
 * @author 27847
 * @version 1.0
 * @date 2019/02/26 22:43
 **/
@Service(interfaceName = "com.pinyougou.service.BrandService")
/** 上面指定接口名，产生服务名，不然会用代理类的名称 */
@Transactional
public class BrandServiceImpl implements BrandService {

    // 注入数据访问接口代理对象
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 添加方法
     *
     * @param brand
     */
    @Override
    public void save(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 修改方法
     *
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 根据主键id删除
     *
     * @param
     */
    @Override
    public void delete(Serializable ids) {

    }

    /**
     * 批量删除
     *
     * @param
     */
    @Override
    public void deleteAll(Serializable[] ids) {
        try {
            /*for (Serializable id : ids) {
                brandMapper.deleteByPrimaryKey(id);
            }*/
            brandMapper.deleteAll(ids);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Brand findOne(Serializable id) {
        return null;
    }

    /**
     * 查询所有品牌
     *
     * @return java.util.List<com.pinyougou.pojo.Brand>
     * @author 27847
     * @date 2019/02/26 22:41
     **/
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /**
     * 多条件分页查询
     *
     * @param brand
     * @param page
     * @param rows
     */
    @Override
    public PageResult findByPage(Brand brand, int page, int rows) {
        try {
            PageInfo<Brand> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    brandMapper.findAll(brand);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Map<String, Object>> findBrandList() {
        try{
            /* 第一种方法,不用写SQL语句，不推荐
           List<Map<String, Object>> data = new ArrayList<>();
           List<Brand> brandList = brandMapper.selectAll();
           for (Brand brand : brandList) {
               Map<String,Object> map = new HashMap<>();
               map.put("id", brand.getId());
               map.put("text", brand.getName());
               data.add(map);
           }
           return data;
            */
            // 第二种方法，需要写SQL语句，推荐使用
            return brandMapper.findBrandList();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
