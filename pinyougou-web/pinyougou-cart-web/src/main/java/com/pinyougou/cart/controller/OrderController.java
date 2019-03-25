package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Order;
import com.pinyougou.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单控制器
 * @author 27847
 * @version 1.0
 * @date 2019/03/25 16:48
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference(timeout = 10000)
    private OrderService orderService;
    /**
     * 保存订单到订单表和订单明细表
     * @author 27847
     * @date 2019/03/25 16:53
     * @param order, request
     * @return boolean
     **/
    @PostMapping("/save")
    public boolean save(@RequestBody Order order, HttpServletRequest request){
        try {
            // 获取用户id（用户名）
            String userId = request.getRemoteUser();
            order.setUserId(userId);
            // 设置订单来源于PC端
            order.setSourceType("2");
            orderService.save(order);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
