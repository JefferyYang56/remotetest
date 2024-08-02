package com.jeffery.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.jeffery.spzx.manager.service.ValidateCodeService;
import com.jeffery.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        //使用工具类生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150,48,4,2);
        String codeValue = captcha.getCode();
        String image = captcha.getImageBase64();
        String key = UUID.randomUUID().toString().replace("-","");
        System.out.println(key);
        redisTemplate.opsForValue().set("user:validate"+key,codeValue,5, TimeUnit.MINUTES);
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + image);
        return validateCodeVo;
    }
}
