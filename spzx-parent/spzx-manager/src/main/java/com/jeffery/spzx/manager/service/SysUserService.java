package com.jeffery.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.jeffery.spzx.model.dto.system.AssginRoleDto;
import com.jeffery.spzx.model.dto.system.LoginDto;
import com.jeffery.spzx.model.dto.system.SysUserDto;
import com.jeffery.spzx.model.entity.system.SysUser;
import com.jeffery.spzx.model.vo.system.LoginVo;
import com.jeffery.spzx.model.vo.system.ValidateCodeVo;

public interface SysUserService {
    public LoginVo login(LoginDto loginDto);


    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);

}
