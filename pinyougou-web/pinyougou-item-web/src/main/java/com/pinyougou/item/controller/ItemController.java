package com.pinyougou.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 商品详情控制器
 * @author 27847
 * @version 1.0
 * @date 2019/03/16 00:54
 **/
@Controller
public class ItemController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;

    /**
     * 根据商品id查询商品信息
     * @author 27847
     * @date 2019/03/16 16:42
     * @param goodsId
     * @return java.lang.String
     **/
    @GetMapping("/{goodsId}")
    public String getGoods(@PathVariable("goodsId") Long goodsId, Model model){
        Map<String,Object> data =  goodsService.getGoods(goodsId);
        model.addAllAttributes(data);
        return "item";
    }
}