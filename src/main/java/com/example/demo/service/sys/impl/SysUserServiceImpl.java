package com.example.demo.service.sys.impl;

import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.dao.sys.SysUserDAO;
import com.example.demo.dal.entity.main.sys.SysUser;
import com.example.demo.service.base.impl.BaseServiceImpl;
import com.example.demo.service.sys.SysUserService;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

/**
 * SysUserService
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    /**
     * SysUserDAO
     */
    @Resource
    private SysUserDAO sysUserDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<SysUser> getDAO() {
        return sysUserDAO;
    }

    @Override
    public SysUser login(String username, String password) {
        SysUser sysUser = sysUserDAO.login(username, password);
        if (sysUser != null) {
            if (matches(sysUser, password)) {
                return sysUser;
            } else {
                return null;
            }
        }

        return sysUser;
    }

    private boolean matches(SysUser user, String password) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), password, user.getSalt()));

    }

    private String encryptPassword(String loginName, String password, String salt) {
        return new Md5Hash(loginName + password + salt).toHex();
    }
}