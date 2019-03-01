package com.example.demo.service.sys.impl;

import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.dao.sys.SysMenuDAO;
import com.example.demo.dal.entity.main.sys.SysMenu;
import com.example.demo.service.base.impl.BaseServiceImpl;
import com.example.demo.service.sys.SysMenuService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * SysMenuService
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {
    
    /**
     * SysMenuDAO
     */
    @Resource
    private SysMenuDAO sysMenuDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<SysMenu> getDAO() {
        return sysMenuDAO;
    }
}