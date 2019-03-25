package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.itheima.common.util.HttpClientUtils;
import com.pinyougou.mapper.UserMapper;
import com.pinyougou.pojo.User;
import com.pinyougou.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/03/19 17:21
 **/
@Service(interfaceName = "com.pinyougou.service.UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.signName}")
    private String signName;
    @Value("${sms.templateCode}")
    private String templateCode;

    /**
     * 添加方法
     *
     * @param user
     */
    @Override
    public void save(User user) {
        try {
            // 密码需要 MD5加密
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            // 创建时间
            user.setCreated(new Date());
            // 修改时间
            user.setUpdated(user.getCreated());
            // 添加用户
            userMapper.insertSelective(user);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 修改方法
     *
     * @param user
     */
    @Override
    public void update(User user) {

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
    public User findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<User> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param user
     * @param page
     * @param rows
     */
    @Override
    public List<User> findByPage(User user, int page, int rows) {
        return null;
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    @Override
    public boolean sendSmsCode(String phone) {
        try {
            /* 1.生成6位随机数字的验证码*/
            String code = UUID.randomUUID().toString().replaceAll(
                    "-", "").replaceAll("[a-z|A-Z]",
                    "").substring(0, 6);
            System.out.println("验证码code：" + code);
            /* 2.调用短信发送接口 */
            // 创建 HttpClientUtils对象
            HttpClientUtils httpClientUtils = new HttpClientUtils(false);
            // 定义Map集合封装请求参数
            Map<String, String> params = new HashMap<>();
            params.put("phone", phone);
            params.put("signName", signName);
            params.put("templateCode", templateCode);
            params.put("templateParam", "{\"number\":\"" + code + "\"}");
            // 调用短信接口
            String content = httpClientUtils
                    .sendPost(smsUrl, params);
            System.out.println(content);
            /* 3.如果发送成功，把验证码存储到redis数据库*/
            // 把json字符串转换为map类型
            Map map = JSON.parseObject(content, Map.class);
            boolean success = (boolean) map.get("success");
            if (success) {
                // 存入redis90秒
                redisTemplate.boundValueOps(phone).set(code, 90, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 检验短信验证码
     *
     * @param phone
     * @param code
     */
    @Override
    public boolean checkSmsCode(String phone, String code) {
        try {
            // 从redis数据库获取短信验证码
            String oldCode = (String) redisTemplate.boundValueOps(phone).get();
            return oldCode != null && oldCode.equals(code);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
