package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.User;
import com.pinyougou.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * 用户中心控制器
 * @author 27847
 * @version 1.0
 * @date 2019/03/19 17:15
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(timeout = 10000)
    private UserService userService;
    /**
     * 保存用户
     * @author 27847
     * @date 2019/03/19 17:18
     * @param user
     * @return boolean
     **/
    @PostMapping("/save")
    public boolean save(@RequestBody User user,String code){
        try {
            boolean ok = userService.checkSmsCode(user.getPhone(),code);
            if (ok) {
                userService.save(user);
            }
            return ok;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 发送短信验证码的方法
     * @author 27847
     * @date 2019/03/19 19:50
     * @param phone
     * @return boolean
     **/
    @GetMapping("/sendSmsCode")
    public boolean sendSmsCode(String phone){
        try {
           return userService.sendSmsCode(phone);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
