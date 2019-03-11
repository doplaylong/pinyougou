package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页轮播图控制器
 * @author 27847
 * @version 1.0
 * @date 2019/03/10 20:17
 **/

@RestController
public class ContentController {

    @Reference(timeout = 10000)
    private ContentService contentService;
    /**
     * 根据广告分类 id 查询广告内容
     * @author 27847
     * @date 2019/03/10 20:31
     * @param
     * @return java.util.List<com.pinyougou.pojo.Content>
     **/
    @RequestMapping("/findContentByCategoryId")
    public List<Content> findContentByCategoryId(Long categoryId){
        return contentService.findContentByCategoryId(categoryId);
    }
}
