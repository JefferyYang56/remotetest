package com.jeffery.spzx.manager.config;

import com.alibaba.fastjson.JSON;
import com.jeffery.spzx.model.entity.system.SysUser;
import com.jeffery.spzx.model.vo.common.Result;
import com.jeffery.spzx.model.vo.common.ResultCodeEnum;
import com.jeffery.spzx.util.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

//拦截器不仅要注册为component 还要在实现了WebMVCConfigurer类中注册
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private  RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //如果失败 要拒绝 response.setContentType setCharacterEncoding
        //并用getwirter获取一个printwriter对象
//        System.out.println(request.getHeader("token"));
//        if("options".equals(request.getMethod())){
//            return true;
//        }
//
//        System.out.println(request.getHeader("token"));
       String token = request.getHeader("token");
//        if(token==null){
//            responseNoLoginInfo(response);
//            return false;
//        }
        String sysUser = redisTemplate.opsForValue().get("user:login"+token);
//        if(sysUser==null){
//            responseNoLoginInfo(response);
//            return false;
//        }
        AuthContextUtil.setSysUser(JSON.parseObject(sysUser,SysUser.class));
        redisTemplate.expire("user:login"+token,30, TimeUnit.MINUTES);
        return true;
    }
    private void responseNoLoginInfo(HttpServletResponse response){
        Result result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(JSON.toJSONString(result));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.removeSysUser();
    }
}
