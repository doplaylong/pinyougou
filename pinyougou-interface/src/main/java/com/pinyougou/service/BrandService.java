package com.pinyougou.service;

import com.pinyougou.pojo.Brand;

import java.util.List;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/02/26 22:41
 **/
public interface BrandService {

    /**
     * 查询所有品牌
     * @author 27847
     * @date 2019/02/26 22:41
     * @param
     * @return java.util.List<com.pinyougou.pojo.Brand>
     **/
    List<Brand> findAll();
}
