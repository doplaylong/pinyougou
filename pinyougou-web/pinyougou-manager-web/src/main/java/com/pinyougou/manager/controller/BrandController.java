package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/02/26 22:46
 **/
@RestController
public class BrandController {

    /**
     *
     * 引用服务接口代理对象
     *  timeout: 调用服务接口方法超时时间毫秒数
     **/
    @Reference(timeout = 10000)
    private BrandService brandService;
    /**
     * 查询全部品牌
     * @author 27847
     * @date 2019/02/26 22:50
     * @param
     * @return java.util.List<com.pinyougou.pojo.Brand>
     **/
    @GetMapping("/brand/findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

}
