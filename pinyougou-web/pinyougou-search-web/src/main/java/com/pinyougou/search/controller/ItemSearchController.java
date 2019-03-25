package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.ItemSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品搜索控制器
 * @author 27847
 * @version 1.0
 * @date 2019/03/13 15:51
 **/

@RestController
public class ItemSearchController {

    @Reference(timeout = 10000)
    private ItemSearchService itemSearchService;

    @PostMapping("/Search")
    public Map<String,Object> search(@RequestBody Map<String,Object> pamars){
        return itemSearchService.search(pamars);
    }
}
