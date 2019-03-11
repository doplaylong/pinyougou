package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.pojo.PageResult;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 模板表控制器
 *
 * @author 27847
 * @version 1.0
 * @date 2019/03/06 16:34
 **/
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    // 引入服务接口
    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    /**
     * 多条件分页查询
     *
     * @param
     * @return com.itheima.common.pojo.PageResult
     * @author 27847
     * @date 2019/03/06 17:02
     **/
    @GetMapping("/findByPage")
    public PageResult findByPage(TypeTemplate typeTemplate, Integer page, Integer rows) {
        if (typeTemplate != null && StringUtils.isNoneBlank(typeTemplate.getName())) {
            try {
                typeTemplate.setName(new String(typeTemplate.getName().getBytes("ISO8859-1"), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return typeTemplateService.findByPage(typeTemplate, page, rows);
    }

    /**
     * 添加
     * @author 27847
     * @date 2019/03/08 15:54
     * @param typeTemplate
     * @return boolean
     **/
    @PostMapping("/save")
    public boolean save(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.save(typeTemplate);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 修改
     * @author 27847
     * @date 2019/03/08 16:03
     * @param typeTemplate
     * @return boolean
     **/
    @PostMapping("/update")
    public boolean update(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.update(typeTemplate);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 批量删除
     * @author 27847
     * @date 2019/03/08 16:22
     * @param ids
     * @return boolean
     **/
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            typeTemplateService.deleteAll(ids);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
