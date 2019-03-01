package com.example.demo.dal.mapper.main.sys;

import com.example.demo.dal.entity.main.sys.SysUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserMapper extends Mapper<SysUser> {
    SysUser login(@Param("username") String username, String password);
}