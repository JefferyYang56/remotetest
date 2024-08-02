package com.jeffery.spzx.manager.controller;

import com.jeffery.spzx.manager.service.SysMenuService;
import com.jeffery.spzx.manager.service.SysRoleMenuService;
import com.jeffery.spzx.manager.service.ValidateCodeService;
import com.jeffery.spzx.model.dto.system.LoginDto;
import com.jeffery.spzx.model.entity.system.SysUser;
import com.jeffery.spzx.model.vo.common.Result;
import com.jeffery.spzx.model.vo.common.ResultCodeEnum;
import com.jeffery.spzx.model.vo.system.LoginVo;
import com.jeffery.spzx.manager.service.SysUserService;
import com.jeffery.spzx.model.vo.system.SysMenuVo;
import com.jeffery.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    ValidateCodeService validateCodeService;
    @Autowired
    SysMenuService sysMenuService;
    @PostMapping("/login")

    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> list = sysMenuService.findMenusByUserId();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/generateValidateCode")

    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader(name="token")String token){
        SysUser sysUser = sysUserService.getUserInfo(token);
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name="token")String token){
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
