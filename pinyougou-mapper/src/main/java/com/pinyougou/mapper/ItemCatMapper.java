package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.ItemCat;

import java.util.List;

/**
 * ItemCatMapper 数据访问接口
 * @date 2019-03-01 22:13:35
 * @version 1.0
 */
public interface ItemCatMapper extends Mapper<ItemCat>{


    @Select("select * from tb_item_cat where parent_id = #{parentId}")
    List<ItemCat> findItemCatByParentId(Long parentId);
}