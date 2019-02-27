package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据访问接口
 * @author 27847
 * @version 1.0
 * @date 2019/02/26 22:34
 **/
public interface BrandMapper {
    /**
     * 查询全部品牌
     * @author 27847
     * @date 2019/02/26 22:36
     * @param
     * @return java.util.List<com.pinyougou.pojo.Brand>
     **/
    @Select("select * from tb_brand order by id asc")
    List<Brand> findAll();
}
