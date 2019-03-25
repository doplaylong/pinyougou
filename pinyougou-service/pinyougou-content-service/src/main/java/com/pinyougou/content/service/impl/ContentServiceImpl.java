package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 27847
 * @version 1.0
 * @date 2019/03/10 21:16
 **/
@Service(interfaceName = "com.pinyougou.service.ContentService")
@Transactional
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 添加方法
     *
     * @param content
     */
    @Override
    public void save(Content content) {
        try {

            // 清除Redis缓存
            redisTemplate.delete("contents");
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 修改方法
     *
     * @param content
     */
    @Override
    public void update(Content content) {
        try {

            // 清除Redis缓存
            redisTemplate.delete("contents");
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 根据主键id删除
     *
     * @param id
     */
    @Override
    public void delete(Serializable id) {

    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteAll(Serializable[] ids) {
        try {

            // 清除Redis缓存
            redisTemplate.delete("contents");
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Content findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Content> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param content
     * @param page
     * @param rows
     */
    @Override
    public List<Content> findByPage(Content content, int page, int rows) {
        return null;
    }

    /**
     * 根据分类id查询广告内容
     * @author 27847
     * @date 2019/03/10 21:17
     * @param categoryId
     * @return java.util.List<com.pinyougou.pojo.Content>
     **/
    @Override
    public List<Content> findContentByCategoryId(Long categoryId) {

        List<Content> contentList = null;
        try {
             contentList = (List<Content>) redisTemplate.boundValueOps("contents").get();
             if (contentList != null && contentList.size() > 0) {
                 return contentList;
             }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        try {
            // 创建Example对象
            Example example = new Example(Content.class);
            // 创建条件对象
            Example.Criteria criteria = example.createCriteria();
            // 添加条件
            criteria.andEqualTo("categoryId", categoryId);
            criteria.andEqualTo("status", 1);
            example.orderBy("sortOrder").asc();
            // 条件查询
            contentList = contentMapper.selectByExample(example);
            //return contentMapper.findContentByCategoryId(categoryId);
            try {
                redisTemplate.boundValueOps("contents").set(contentList);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return contentList;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
