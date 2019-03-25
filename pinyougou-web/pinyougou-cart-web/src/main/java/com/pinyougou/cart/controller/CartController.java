package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.common.util.CookieUtils;
import com.pinyougou.cart.Cart;
import com.pinyougou.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车控制器
 *
 * @author 27847
 * @version 1.0
 * @date 2019/03/22 16:50
 **/
@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 30000)
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;


    // 添加sku商品到购物车
    @GetMapping("/addCart")
    public boolean addCart(Long itemId, Integer num) {
        /* 设置允许访问的域名 */
        response.setHeader("Access-Control-Allow-Origin", "http://item.pinyougou.com");
        /* 设置允许操作Cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            // 获取登录用户名
            String username = request.getRemoteUser();
            // 获取购物车集合
            List<Cart> carts = findCart();
            // 调用服务层添加sku商品到购物车
            carts = cartService.addItemToCart(carts, itemId, num);
            // 判断用户是否已登录
            if (StringUtils.isNoneBlank(username)) {
                // 如果登录把购物车存入Redis
                cartService.saveCartToRedis(username,carts);
            }else {
            // 将购物车重新存入Cookie中
            CookieUtils.setCookie(request, response, CookieUtils.CookieName.PINYOUGOU_CART,
                    JSON.toJSONString(carts), 3600 * 24, true);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /* 获取购物车集合 */
    @GetMapping("findCart")
    public List<Cart> findCart() {
        // 获取登录用户名
        String username = request.getRemoteUser();
        // 定义购物车集合
        List<Cart> carts = null;
        // 判断用户是否已登录
        if (StringUtils.isNoneBlank(username)) {
            carts = cartService.findCartRedis(username);
            // 获取cookie中的购物车
            String cartStr = CookieUtils.getCookieValue(request,
                    CookieUtils.CookieName.PINYOUGOU_CART, true);
            if (StringUtils.isNoneBlank(cartStr)) {
                List<Cart> cookieCarts = JSON.parseArray(cartStr, Cart.class);
                if (cookieCarts != null && cookieCarts.size() > 0) {
                    // 合并购物车
                    carts = cartService.mergeCart(cookieCarts,carts);
                    // 把合并后的购物车存入redis中
                    cartService.saveCartToRedis(username, carts);
                    // 删除cookie
                    CookieUtils.deleteCookie(request, response,
                            CookieUtils.CookieName.PINYOUGOU_CART);
                }
            }
        }else {
        // 从Cookie中获取购物车集合json字符串
        String cartStr = CookieUtils.getCookieValue(request,
                CookieUtils.CookieName.PINYOUGOU_CART, true);
        // 判断是否为空
        if (StringUtils.isBlank(cartStr)) {
            cartStr = "[]";
        }
        carts = JSON.parseArray(cartStr, Cart.class);
        }
        return carts;
    }
}
