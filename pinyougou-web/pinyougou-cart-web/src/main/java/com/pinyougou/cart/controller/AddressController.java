package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Address;
import com.pinyougou.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 地址控制器
 * @author 27847
 * @version 1.0
 * @date 2019/03/24 17:14
 **/
@RestController
@RequestMapping("/order")
public class AddressController {

    @Reference(timeout = 10000)
    private AddressService addressService;
    /**
     * 根据用户名查询地址
     * @author 27847
     * @date 2019/03/24 17:32
     * @param request
     * @return java.util.List<com.pinyougou.pojo.Address>
     **/
    @GetMapping("/findAddressByUser")
    public List<Address> findAddressByUser(HttpServletRequest request){
        // 获取登录用户名
        String userId = request.getRemoteUser();
        // 获取地址
        return addressService.findAddressByUser(userId);
    }
}
