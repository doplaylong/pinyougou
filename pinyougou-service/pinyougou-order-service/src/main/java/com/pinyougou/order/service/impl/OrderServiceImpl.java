package com.pinyougou.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.Cart;
import com.pinyougou.mapper.OrderMapper;
import com.pinyougou.pojo.Order;
import com.pinyougou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 订单接口实现类
 * @author 27847
 * @version 1.0
 * @date 2019/03/25 17:03
 **/
@Service(interfaceName = "com.pinyougou.service.OrderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加方法
     *
     * @param order
     */
    @Override
    public void save(Order order) {
        try {
            List<Cart> carts = (List<Cart>) redisTemplate.boundValueOps(order.getUserId()).get();


        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 修改方法
     *
     * @param order
     */
    @Override
    public void update(Order order) {

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

    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Order findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Order> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param order
     * @param page
     * @param rows
     */
    @Override
    public List<Order> findByPage(Order order, int page, int rows) {
        return null;
    }
}
