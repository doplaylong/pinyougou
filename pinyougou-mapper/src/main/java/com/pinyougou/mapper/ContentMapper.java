package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Content;

import java.util.List;

/**
 * ContentMapper 数据访问接口
 * @date 2019-03-01 22:13:35
 * @version 1.0
 */
public interface ContentMapper extends Mapper<Content>{
    /*根据分类id查询广告内容*/
    @Select("SELECT * FROM tb_content WHERE STATUS = '1' AND " +
            "category_id = #{categoryId} ORDER BY sort_order ASC")
    List<Content> findContentByCategoryId(Long categoryId);
}