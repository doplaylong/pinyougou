package com.pinyougou.service;

import java.util.Map;

/**
 * 商品搜索服务接口
 * @author 27847
 * @version 1.0
 * @date 2019/03/13 16:00
 **/
public interface ItemSearchService {

    /*商品条件搜索*/
    Map<String,Object> search(Map<String, Object> pamars);
}
