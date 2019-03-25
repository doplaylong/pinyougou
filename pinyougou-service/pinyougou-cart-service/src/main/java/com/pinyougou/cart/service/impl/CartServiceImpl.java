package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.Cart;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.pojo.OrderItem;
import com.pinyougou.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务接口实现类
 *
 * @author 27847
 * @version 1.0
 * @date 2019/03/22 17:34
 **/
@Service(interfaceName = "com.pinyougou.service.CartService")
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加sku商品到购物车
     *
     * @param carts  购物车(一个cart对应一个商家)
     * @param itemId sku商品id
     * @param num    购买数量
     * @return java.util.List<com.pinyougou.cart.Cart> 修改后的购物车
     **/
    @Override
    public List<Cart> addItemToCart(List<Cart> carts, Long itemId, Integer num) {
        try {
            // 1.根据sku商品ID查询sku商品对象
            Item item = itemMapper.selectByPrimaryKey(itemId);
            // 2.获取商家id
            String sellerId = item.getSellerId();
            // 3.根据商家id判断购物车集合中是否存在该商家的购物车
            Cart cart = searchCartBySellerId(carts, sellerId);
            // 4.如果购物车集合中不存在该商家的购物车
            if (cart == null) {
                // 4.1创建新的购物车对象
                cart = new Cart();
                // 4.2创建订单商品明细集合封装数据
                OrderItem orderItem = createOrderItem(item, num);
                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(orderItem);
                // 4.3将新的购物车对象添加到购物车集合
                cart.setSellerId(sellerId);
                cart.setSellerName(item.getSeller());
                cart.setOrderItems(orderItems);
                carts.add(cart);
            } else {
                // 5.如果购物车集合中存在该商家的购物车
                // 5.1判断购物车订单明细集合中是否存在该商品
                OrderItem orderItem = searchOrderItemByItemId(cart.getOrderItems(), itemId);
                if (orderItem == null) {
                    // 5.2如果没有，新增购物车订单明细
                    orderItem = createOrderItem(item, num);
                    cart.getOrderItems().add(orderItem);
                } else {
                    // 5.3如果有，在原购物车订单明细上添加数量，更改金额
                    orderItem.setNum(orderItem.getNum() + num);
                    orderItem.setTotalFee(new BigDecimal(orderItem.getNum() * orderItem.getPrice().doubleValue()));
                    // 5.4如果商品数量小于等于0，则从该商家的购物车集中中删除该商品
                    if (orderItem.getNum() <= 0) {
                        cart.getOrderItems().remove(orderItem);
                    }
                    // 5.5如果商家的购物车商品明细为0，则从用户的购物车集合中删除该商家的购物车
                    if (cart.getOrderItems().size() == 0) {
                        carts.remove(cart);
                    }
                }
            }
            return carts;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 从Redis中获取购物车集合
     * @param username
     * @return java.util.List<com.pinyougou.cart.Cart>
     * @author 27847
     * @date 2019/03/23 17:46
     **/
    @Override
    public List<Cart> findCartRedis(String username) {
        List<Cart> carts = (List<Cart>) redisTemplate.boundValueOps(username).get();
        if (carts == null) {
            carts = new ArrayList<>();
        }
        return carts;
    }

    /**
     * 将购物车存入Redis
     * @param username
     * @param carts
     * @return void
     * @author 27847
     * @date 2019/03/23 18:14
     **/
    @Override
    public void saveCartToRedis(String username, List<Cart> carts) {
        redisTemplate.boundValueOps(username).set(carts);
    }

    /**
     * 合并购物车
     *
     * @param cookieCarts
     * @param redisCarts
     * @return java.util.List<com.pinyougou.cart.Cart>
     * @author 27847
     * @date 2019/03/23 20:10
     **/
    @Override
    public List<Cart> mergeCart(List<Cart> cookieCarts, List<Cart> redisCarts) {
        for (Cart cookieCart : cookieCarts) {
            for (OrderItem orderItem : cookieCart.getOrderItems()) {
                redisCarts = addItemToCart(redisCarts, orderItem.getItemId(), orderItem.getNum());
            }
        }
        return redisCarts;
    }

    // 根据商家id在购物车集中获取相应的购物车对象
    private Cart searchCartBySellerId(List<Cart> carts, String sellerId) {
        for (Cart cart : carts) {
            if (sellerId.equals(cart.getSellerId())) {
                return cart;
            }
        }
        return null;
    }

    // 根据item和购买数量num转化为订单明细OrderItem
    private OrderItem createOrderItem(Item item, Integer num) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(item.getId());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setNum(num);
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue() * num));
        orderItem.setPicPath(item.getImage());
        orderItem.setSellerId(item.getSellerId());
        return orderItem;
    }

    // 从订单明细集合中获取指定订单明细
    private OrderItem searchOrderItemByItemId(List<OrderItem> orderItems, Long itemId) {
        for (OrderItem orderItem : orderItems) {
            if (itemId.equals(orderItem.getItemId())) {
                return orderItem;
            }
        }
        return null;
    }
}
