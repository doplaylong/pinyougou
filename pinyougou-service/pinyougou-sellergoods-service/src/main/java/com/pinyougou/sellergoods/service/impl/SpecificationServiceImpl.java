package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/03/04 19:57
 **/
@Service(interfaceName = "com.pinyougou.service.SpecificationService")
@Transactional
public class SpecificationServiceImpl implements SpecificationService {
    // 注入数据访问接口代理对象
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 添加方法
     *
     * @param specification
     */
    @Override
    public void save(Specification specification) {
        try {
            specificationMapper.insertSelective(specification);
       /* for (SpecificationOption specificationOption : specification.getSpecificationOptions()) {
            specificationOption.setSpecId(specification.getId());
            specificationOptionMapper.insertSelective(specificationOption);
        }*/
            specificationOptionMapper.save(specification);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 修改规则表和规格选项表数据
     *
     * @param specification
     */
    @Override
    public void update(Specification specification) {
        try {
            // 修改规格数据
            specificationMapper.updateByPrimaryKeySelective(specification);
            // 修改规格选项表数据
            // 1.封装条件，删除原来的数据
            SpecificationOption so = new SpecificationOption();
            so.setSpecId(specification.getId());
            specificationOptionMapper.delete(so);
            // 插入新数据
            specificationOptionMapper.save(specification);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
     * 批量删除规格与规格选项
     *
     * @param ids
     */
    @Override
    public void deleteAll(Serializable[] ids) {
        try {
            for (Serializable id : ids) {
                SpecificationOption so = new SpecificationOption();
                so.setSpecId((Long) id);
                specificationOptionMapper.delete(so);
                specificationMapper.deleteByPrimaryKey(id);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Specification findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Specification> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param specification
     * @param page
     * @param rows
     */
    @Override
    public PageResult findByPage(Specification specification, int page, int rows) {
        try {
            PageInfo<Specification> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    specificationMapper.findAll(specification);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 根据规格主键查询规格选项
     *
     * @param id
     */
    @Override
    public List<SpecificationOption> findSpecOption(Long id) {
        try {
            SpecificationOption so = new SpecificationOption();
            so.setSpecId(id);
            return specificationOptionMapper.select(so);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 查找所有规格id和name
     * @author 27847
     * @date 2019/03/07 16:46
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> findSpecList() {
        try {
            return specificationMapper.findSpecList();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
