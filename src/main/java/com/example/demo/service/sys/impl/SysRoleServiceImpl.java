package com.example.demo.service.sys.impl;

import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.dao.sys.SysRoleDAO;
import com.example.demo.dal.entity.main.sys.SysRole;
import com.example.demo.service.base.impl.BaseServiceImpl;
import com.example.demo.service.sys.SysRoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * SysRoleService
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
    
    /**
     * SysRoleDAO
     */
    @Resource
    private SysRoleDAO sysRoleDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<SysRole> getDAO() {
        return sysRoleDAO;
    }
}