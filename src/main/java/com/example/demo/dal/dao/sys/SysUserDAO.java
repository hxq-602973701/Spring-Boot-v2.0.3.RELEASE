package com.example.demo.dal.dao.sys;

import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.entity.main.sys.SysUser;

/**
 * SysUserDAO
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface SysUserDAO extends BaseDAO<SysUser> {

    SysUser login(String username, String password);
}