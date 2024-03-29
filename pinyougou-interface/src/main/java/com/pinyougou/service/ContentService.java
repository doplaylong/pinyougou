package com.pinyougou.service;

import com.pinyougou.pojo.Content;
import java.util.List;
import java.io.Serializable;
/**
 * ContentService 服务接口
 * @date 2019-03-01 22:17:35
 * @version 1.0
 */
public interface ContentService {

	/** 添加方法 */
	void save(Content content);

	/** 修改方法 */
	void update(Content content);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Content findOne(Serializable id);

	/** 查询全部 */
	List<Content> findAll();

	/** 多条件分页查询 */
	List<Content> findByPage(Content content, int page, int rows);

	/** 根据广告分类 id 查询广告内容 */
	List<Content> findContentByCategoryId(Long categoryId);
}