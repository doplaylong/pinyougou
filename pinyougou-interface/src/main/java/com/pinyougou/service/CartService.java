package com.pinyougou.service;

import com.pinyougou.cart.Cart;

import java.util.List; /**
 * 购物车服务接口
 * @author 27847
 * @version 1.0
 * @date 2019/03/22 17:23
 **/
public interface CartService {
    /**
     * 添加sku商品到购物车
     * @param carts 购物车(一个cart对应一个商家)
     * @param itemId sku商品id
     * @param num 购买数量
     * @return java.util.List<com.pinyougou.cart.Cart> 修改后的购物车
     **/
    List<Cart> addItemToCart(List<Cart> carts, Long itemId, Integer num);

    /**
     * 从Redis中获取购物车集合
     * @author 27847
     * @date 2019/03/23 17:46
     * @param username
     * @return java.util.List<com.pinyougou.cart.Cart>
     **/
    List<Cart> findCartRedis(String username);

    /**
     * 将购物车存入Redis
     * @author 27847
     * @date 2019/03/23 18:14
     * @param username, carts
     * @return void
     **/
    void saveCartToRedis(String username, List<Cart> carts);

    /**
     * 合并购物车
     * @author 27847
     * @date 2019/03/23 20:10
     * @param cookieCarts, redisCarts
     * @return java.util.List<com.pinyougou.cart.Cart>
     **/
    List<Cart> mergeCart(List<Cart> cookieCarts, List<Cart> redisCarts);

}
