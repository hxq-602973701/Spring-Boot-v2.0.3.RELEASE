package com.example.demo.dal.dao.sys.impl;

import com.example.demo.dal.dao.base.impl.BaseDAOImpl;
import com.example.demo.dal.dao.sys.SysRoleMenuDAO;
import com.example.demo.dal.entity.main.sys.SysRoleMenu;
import com.example.demo.dal.mapper.main.sys.SysRoleMenuMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * SysRoleMenuDAO
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysRoleMenuDAOImpl extends BaseDAOImpl<SysRoleMenu> implements SysRoleMenuDAO {
    
    /**
     * SysRoleMenuMapper
     */
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<SysRoleMenu> getMapper() {
        return sysRoleMenuMapper;
    }
}