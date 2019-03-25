package com.pinyougou.cart;

import com.pinyougou.pojo.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车实体类
 * @author 27847
 * @version 1.0
 * @date 2019/03/21 19:29
 **/
public class Cart implements Serializable{
    /* 商家ID */
    private String sellerId;
    /* 商家名称 */
    private String sellerName;
    /* 购物车明细集合 */
    private List<OrderItem> orderItems;

    public Cart() {
    }

    public Cart(String sellerId, String sellerName, List<OrderItem> orderItems) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.orderItems = orderItems;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
