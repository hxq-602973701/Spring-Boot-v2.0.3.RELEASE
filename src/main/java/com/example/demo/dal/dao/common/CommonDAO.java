package com.example.demo.dal.dao.common;


import com.example.demo.dal.datasource.DataSourceEnum;
import com.example.demo.dal.entity.base.BaseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用DAO
 *
 * @author lt on 2016/6/3.
 * @version 1.0.0
 */
public interface CommonDAO {

    /**
     * 根据条件获取指定表的数据
     * <p>
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param conditionMap 条件
     * @param limit        最多返回条数
     * @return
     */
    List<HashMap> selectByCondition(DataSourceEnum dataSource, String tableName, Map conditionMap, Integer limit);

    /**
     * 根据条件更新指定表的记录
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param updateMap    更新内容
     * @param conditionMap 条件
     */
    int updateByCondition(DataSourceEnum dataSource, String tableName, Map updateMap, Map conditionMap);

}
