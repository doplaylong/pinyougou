package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SpecificationMapper 数据访问接口
 * @date 2019-03-01 22:13:35
 * @version 1.0
 */
public interface SpecificationMapper extends Mapper<Specification>{

    List<Specification> findAll(Specification specification);

    @Select("select id,spec_name as text from tb_specification")
    List<Map<String,Object>> findSpecList();
}