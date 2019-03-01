package com.example.demo.service.sys;

import com.example.demo.dal.entity.main.sys.SysUser;
import com.example.demo.service.base.BaseService;

/**
 * SysUserService
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 登录验证
     *
     * @param username
     * @param password
     * @return
     */
    SysUser login(String username, String password);
}