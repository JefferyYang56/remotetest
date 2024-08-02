package com.jeffery.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeffery.spzx.manager.mapper.SysRoleMapper;
import com.jeffery.spzx.manager.mapper.SysRoleUserMapper;
import com.jeffery.spzx.manager.service.SysRoleService;
import com.jeffery.spzx.model.dto.system.SysRoleDto;
import com.jeffery.spzx.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    //4 角色删除的方法
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.delete(roleId);
    }

    //查询所有角色
    @Override
    public Map<String, Object> findAll(Long userId) {
        //1 查询所有角色
        List<SysRole> roleList =  sysRoleMapper.findAll();

        //2 分配过的角色列表
        //根据userId查询用户分配过角色id列表
        List<Long> roleIds = sysRoleUserMapper.selectRoleIdsByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("allRolesList",roleList);
        map.put("sysUserRoles",roleIds);
        return map;
    }

}
