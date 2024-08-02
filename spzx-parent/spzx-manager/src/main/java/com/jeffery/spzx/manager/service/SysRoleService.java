package com.jeffery.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.jeffery.spzx.model.dto.system.SysRoleDto;
import com.jeffery.spzx.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, int page, int limit);

    void saveSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    void updateSysRole(SysRole sysRole);

    Map<String, Object> findAll(Long userId);
}
