package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.pojo.PageResult;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 规格控制器层
 *
 * @author 27847
 * @version 1.0
 * @date 2019/03/05 13:23
 **/
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference(timeout = 10000)
    private SpecificationService specificationService;

    /**
     * 多条件分页查询
     *
     * @param
     * @return com.itheima.common.pojo.PageResult
     * @author 27847
     * @date 2019/03/05 13:41
     **/
    @GetMapping("/findByPage")
    public PageResult findByPage(Specification specification, Integer page, Integer rows) {
        // Get请求中文乱码
        if (specification != null && StringUtils.isNoneBlank(specification.getSpecName())) {
            try {
                specification.setSpecName(new String(specification.getSpecName().
                        getBytes("ISO8859-1"), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return specificationService.findByPage(specification, page, rows);
    }

    /**
     * 添加
     *
     * @param
     * @return boolean
     * @author 27847
     * @date 2019/03/05 14:40
     **/
    @PostMapping("/save")
    public boolean save(@RequestBody Specification specification) {
        try {
            specificationService.save(specification);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 根据规格ID查询规格选项
     *
     * @param
     * @return java.util.List<com.pinyougou.pojo.SpecificationOption>
     * @author 27847
     * @date 2019/03/06 01:45
     **/
    @GetMapping("/findSpecOption")
    public List<SpecificationOption> findSpecOption(Long id) {
        return specificationService.findSpecOption(id);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            specificationService.deleteAll(ids);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * 查找所有规格id和name
     * @author 27847
     * @date 2019/03/07 16:44
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @GetMapping("/findSpecList")
    public List<Map<String,Object>> findSpecList() {
        return specificationService.findSpecList();
    }
}
