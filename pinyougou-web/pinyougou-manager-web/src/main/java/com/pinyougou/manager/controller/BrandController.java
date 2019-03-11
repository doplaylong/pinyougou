package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/02/26 22:46
 **/
@RestController
@RequestMapping("/brand")
public class BrandController {

    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     **/
    @Reference(timeout = 10000)
    private BrandService brandService;

    /**
     * 分页查询全部品牌
     *
     * @param
     * @return java.util.List<com.pinyougou.pojo.Brand>
     * @author 27847
     * @date 2019/02/26 22:50
     **/
    @GetMapping("/findByPage")
    // {total:100,rows:[{},{},{}]}
    public PageResult findByPage(Brand brand, Integer page, Integer rows) {
        // GET请求中文乱码
        if (brand != null && StringUtils.isNoneBlank(brand.getName())) {
            try {
                brand.setName(new String(brand.getName().getBytes("ISO8859-1"), "UTF-8"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return brandService.findByPage(brand, page, rows);
    }

    /**
     * 添加品牌
     *
     * @param
     * @return boolean
     * @author 27847
     * @date 2019/03/02 16:56
     **/
    @PostMapping("/save")
    public boolean save(@RequestBody Brand brand) {
        try {
            brandService.save(brand);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * 修改品牌
     *
     * @param
     * @return boolean
     * @author 27847
     * @date 2019/03/02 19:22
     **/
    @PostMapping("/update")
    public boolean update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     *  批量删除品牌
     * @author 27847
     * @date 2019/03/03 23:37
     * @param
     * @return boolean
     **/
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            brandService.deleteAll(ids);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 查找所有品牌id和名称
     * @author 27847
     * @date 2019/03/07 15:41
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @GetMapping("/findBrandList")
    public List<Map<String,Object>> findBrandList(){
        return brandService.findBrandList();
    }



}
