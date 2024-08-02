package com.jeffery.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.jeffery.spzx.manager.service.SysRoleService;
import com.jeffery.spzx.model.dto.product.SkuSaleDto;
import com.jeffery.spzx.model.dto.system.SysRoleDto;
import com.jeffery.spzx.model.entity.system.SysRole;
import com.jeffery.spzx.model.vo.common.Result;
import com.jeffery.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") int page,
                                    @PathVariable("limit") int limit,
                                    @RequestBody SysRoleDto sysRoleDto){
        PageInfo<SysRole> pageInfo =  sysRoleService.findByPage(sysRoleDto,page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){

        sysRoleService.saveSysRole(sysRole);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    //4 角色删除的方法
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") Long roleId) {
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //3 角色修改的方法
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //查询所有角色
    @GetMapping("/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId") Long userId) {
        Map<String,Object> map = sysRoleService.findAll(userId);
        return Result.build(map,ResultCodeEnum.SUCCESS);
    }
}
