package com.example.demo.dal.dao.sys.impl;

import com.example.demo.dal.dao.base.impl.BaseDAOImpl;
import com.example.demo.dal.dao.sys.SysMenuDAO;
import com.example.demo.dal.entity.main.sys.SysMenu;
import com.example.demo.dal.mapper.main.sys.SysMenuMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * SysMenuDAO
 *
 * @author lt 2019-2-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class SysMenuDAOImpl extends BaseDAOImpl<SysMenu> implements SysMenuDAO {
    
    /**
     * SysMenuMapper
     */
    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<SysMenu> getMapper() {
        return sysMenuMapper;
    }
}