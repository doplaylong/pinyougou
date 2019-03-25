package com.pinyougou.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/03/23 20:40
 **/
@RestController
public class LoginController {

    @GetMapping("/user/showName")
    public Map<String,String> showName(HttpServletRequest request){
        Map<String,String> data = new HashMap<>();
        String username = request.getRemoteUser();
        data.put("loginName", username);
        return data;
    }
}
