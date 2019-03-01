package com.example.demo.dal.dao.sys.impl;

import com.example.demo.dal.dao.base.impl.BaseDAOImpl;
import com.example.demo.dal.dao.sys.SysUserRoleDAO;
import com.example.demo.dal.entity.main.sys.SysUserRole;
import com.example.demo.dal.mapper.main.sys.SysUserRoleMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * SysUserRoleDAO
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysUserRoleDAOImpl extends BaseDAOImpl<SysUserRole> implements SysUserRoleDAO {
    
    /**
     * SysUserRoleMapper
     */
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<SysUserRole> getMapper() {
        return sysUserRoleMapper;
    }
}