package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.AddressMapper;
import com.pinyougou.pojo.Address;
import com.pinyougou.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 地址接口实现类
 * @author 27847
 * @version 1.0
 * @date 2019/03/24 17:37
 **/
@Service(interfaceName = "com.pinyougou.service.AddressService")
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    /**
     * 添加方法
     *
     * @param address
     */
    @Override
    public void save(Address address) {

    }

    /**
     * 修改方法
     *
     * @param address
     */
    @Override
    public void update(Address address) {

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
    public Address findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Address> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param address
     * @param page
     * @param rows
     */
    @Override
    public List<Address> findByPage(Address address, int page, int rows) {
        return null;
    }

    /**
     * 根据用户ID查询地址
     * @author 27847
     * @date 2019/03/24 17:38
     * @param userId
     * @return java.util.List<com.pinyougou.pojo.Address>
     **/
    @Override
    public List<Address> findAddressByUser(String userId) {
        try {
            // 创建地址对象封装查询条件
            Address address = new Address();
            address.setUserId(userId);
            return addressMapper.select(address);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
