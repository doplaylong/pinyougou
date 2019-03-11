package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.SpecificationOption;

/**
 * SpecificationOptionMapper 数据访问接口
 *
 * @version 1.0
 * @date 2019-03-01 22:13:35
 */
public interface SpecificationOptionMapper extends Mapper<SpecificationOption> {

    void save(Specification specification);
}