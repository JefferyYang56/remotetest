package com.jeffery.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeffery.spzx.common.exception.JefferyException;
import com.jeffery.spzx.manager.mapper.SysRoleUserMapper;
import com.jeffery.spzx.manager.mapper.SysUserMapper;
import com.jeffery.spzx.model.dto.system.AssginRoleDto;
import com.jeffery.spzx.model.dto.system.LoginDto;
import com.jeffery.spzx.model.dto.system.SysUserDto;
import com.jeffery.spzx.model.entity.system.SysUser;
import com.jeffery.spzx.model.vo.common.ResultCodeEnum;
import com.jeffery.spzx.model.vo.system.LoginVo;
import com.jeffery.spzx.manager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Override
    public LoginVo login(LoginDto loginDto) {

        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get("user:validate"+key);
        if(StrUtil.isEmpty(redisCode)|| !StrUtil.equalsIgnoreCase(redisCode,captcha)){
            throw new JefferyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        redisTemplate.delete("user:validate"+key);

        String userName = loginDto.getUserName();
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if(sysUser==null){
            throw new JefferyException(ResultCodeEnum.LOGIN_ERROR);
        }
        String password = sysUser.getPassword();
        String inputPassword = loginDto.getPassword();
        inputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if(!password.equals(inputPassword)){
            throw new JefferyException(ResultCodeEnum.LOGIN_ERROR);
        }
        String token = UUID.randomUUID().toString().replace("-","");

        redisTemplate.opsForValue().set("user:login"+token, JSON.toJSONString(sysUser),7, TimeUnit.DAYS);
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userName = redisTemplate.opsForValue().get("user:login"+token);
        //使用alibaba fastjson包中的 toJSONString和JSON.parseObject()
        SysUser sysUser =JSON.parseObject(userName,SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login"+token);
    }

    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum,
                                        Integer pageSize,
                                        SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser>  pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //2 用户添加
    @Override
    public void saveSysUser(SysUser sysUser) {
        //1 判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if(dbSysUser != null) {
            throw new JefferyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //2 输入密码进行加密
        String md5_password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5_password);

        //设置status值  1 可用  0 不可用
        sysUser.setStatus(1);

        sysUserMapper.save(sysUser);
    }

    //3 用户修改
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    //4 用户删除
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }


   //@Log(title = "用户分配角色",businessType = 0)
    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //1 根据userId删除用户之前分配角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());

        //TODO 为了测试，模拟异常
        //int a = 1/0;

        //2 重新分配新数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        //遍历得到每个角色id
        for(Long roleId:roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }
    }

}
