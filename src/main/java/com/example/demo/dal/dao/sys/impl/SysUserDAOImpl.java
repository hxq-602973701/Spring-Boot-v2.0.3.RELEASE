package com.example.demo.dal.dao.sys.impl;

import com.example.demo.dal.dao.base.impl.BaseDAOImpl;
import com.example.demo.dal.dao.sys.SysUserDAO;
import com.example.demo.dal.entity.main.sys.SysUser;
import com.example.demo.dal.mapper.main.sys.SysUserMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * SysUserDAO
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysUserDAOImpl extends BaseDAOImpl<SysUser> implements SysUserDAO {
    
    /**
     * SysUserMapper
     */
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<SysUser> getMapper() {
        return sysUserMapper;
    }

    @Override
    public SysUser login(String username, String password) {
        return sysUserMapper.login(username,password);
    }
}