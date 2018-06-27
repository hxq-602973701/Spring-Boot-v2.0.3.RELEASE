package com.example.demo.dal.dao.common.impl;


import com.example.demo.dal.dao.common.CommonDAO;
import com.example.demo.dal.datasource.DataSourceEnum;
import com.example.demo.dal.datasource.MultipleDataSource;
import com.example.demo.dal.mapper.common.CommonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通用DAO
 *
 * @author lt on 2016/6/3
 * @version 1.0.0
 */
@Service
public class CommonDAOImpl implements CommonDAO {

    /**
     * Get属性方法索引
     */
    private static final int GET_METHOD = 0;
    /**
     * Set属性方法索引
     */
    private static final int SET_METHOD = 1;
    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(CommonDAOImpl.class);
    /**
     * 通用Mapper
     */
    @Resource
    private CommonMapper commonMapper;

    /**
     * 缓存的待生成主键的类字段【Method[] = getMethod、setMethod】
     */
    private Map<Class<?>, Method[]> methodCacheMap = new ConcurrentHashMap<>();

    /**
     * 根据条件获取指定表的数据
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param conditionMap 条件
     * @param limit        最多返回条数
     * @return
     */
    @Override
    public List<HashMap> selectByCondition(final DataSourceEnum dataSource, final String tableName, final Map conditionMap, final Integer limit) {
        MultipleDataSource.setDataSourceKey(dataSource);
        return commonMapper.selectByCondition(dataSource.getValue().toString(), tableName, conditionMap, limit);
    }

    /**
     * 根据条件更新指定表的记录
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param updateMap    更新内容
     * @param conditionMap 条件
     */
    @Override
    public int updateByCondition(final DataSourceEnum dataSource, final String tableName, final Map updateMap, final Map conditionMap) {
        MultipleDataSource.setDataSourceKey(dataSource);
        return commonMapper.updateByCondition(tableName, updateMap, conditionMap);
    }

}
