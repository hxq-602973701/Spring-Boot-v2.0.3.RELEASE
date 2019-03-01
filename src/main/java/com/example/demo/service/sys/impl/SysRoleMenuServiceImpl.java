package com.example.demo.service.sys.impl;

import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.dao.sys.SysRoleMenuDAO;
import com.example.demo.dal.entity.main.sys.SysRoleMenu;
import com.example.demo.service.base.impl.BaseServiceImpl;
import com.example.demo.service.sys.SysRoleMenuService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * SysRoleMenuService
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu> implements SysRoleMenuService {
    
    /**
     * SysRoleMenuDAO
     */
    @Resource
    private SysRoleMenuDAO sysRoleMenuDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<SysRoleMenu> getDAO() {
        return sysRoleMenuDAO;
    }
}