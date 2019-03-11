package com.pinyougou.mapper;

import com.pinyougou.pojo.TypeTemplate;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * TypeTemplateMapper 数据访问接口
 * @date 2019-03-01 22:13:35
 * @version 1.0
 */
public interface TypeTemplateMapper extends Mapper<TypeTemplate>{

    // 多条件查询
    List<TypeTemplate> findAll(TypeTemplate typeTemplate);

    // 批量删除
    void deleteAll(Serializable[] ids);
}