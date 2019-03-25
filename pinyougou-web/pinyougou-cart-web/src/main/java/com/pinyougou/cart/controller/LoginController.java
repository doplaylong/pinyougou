package com.pinyougou.cart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 * @author 27847
 * @version 1.0
 * @date 2019/03/21 21:41
 **/
@RestController
public class LoginController {

    @GetMapping("/user/showName")
    public Map<String,String> showName(HttpServletRequest request){
        Map<String,String> data = new HashMap<>();
        String loginName = request.getRemoteUser();
        data.put("loginName", loginName);
        return data;
    }
}
