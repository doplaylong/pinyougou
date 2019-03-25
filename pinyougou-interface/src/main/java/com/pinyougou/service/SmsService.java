package com.pinyougou.service;

/**
 * 短信服务接口
 *
 * @author 27847
 * @version 1.0
 * @date 2019/03/18 21:21
 **/
public interface SmsService {
    /**
     * 发送短信方法
     * @param phone 手机号码
     * @param signName 签名
     * @param templateCode 短信模板
     * @param templateParam 模板参数 (json格式)
     * @return true 发送成功 false 发送失败
     **/
    public boolean sendSms(String phone,String signName, String templateCode,String templateParam);
}
