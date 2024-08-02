package com.jeffery.spzx.util;

import com.jeffery.spzx.model.entity.system.SysUser;

public class AuthContextUtil {
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    public static SysUser getSysUser() {
        return threadLocal.get();
    }
    public static void setSysUser(SysUser sysUser) {
        threadLocal.set(sysUser);
    }
    public static void removeSysUser() {
        threadLocal.remove();
    }
}
