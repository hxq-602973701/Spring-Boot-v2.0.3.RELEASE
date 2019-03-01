package com.example.demo.dal.dao.sys.impl;

import com.example.demo.dal.dao.base.impl.BaseDAOImpl;
import com.example.demo.dal.dao.sys.SysRoleDAO;
import com.example.demo.dal.entity.main.sys.SysRole;
import com.example.demo.dal.mapper.main.sys.SysRoleMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * SysRoleDAO
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysRoleDAOImpl extends BaseDAOImpl<SysRole> implements SysRoleDAO {
    
    /**
     * SysRoleMapper
     */
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<SysRole> getMapper() {
        return sysRoleMapper;
    }
}