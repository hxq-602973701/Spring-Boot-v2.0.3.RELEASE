package com.example.demo.dal.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用Mapper
 *
 * @author lt 2015-6-1下午1:36:09
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface CommonMapper {

    /**
     * 根据条件获取指定表的数据
     *
     * @param tableName    表名
     * @param conditionMap 条件
     * @param limit        最多返回条数
     * @return
     */
    List<HashMap> selectByCondition(@Param("database") String database, @Param("tableName") String tableName, @Param("conditionMap") Map conditionMap, @Param("limit") Integer limit);

    /**
     * 根据条件更新指定表的记录
     *
     * @param tableName    表名
     * @param updateMap    更新内容
     * @param conditionMap 条件
     */
    int updateByCondition(@Param("tableName") String tableName, @Param("updateMap") Map updateMap, @Param("conditionMap") Map conditionMap);

    /**
     * 根据条件更新指定表的记录(更新条件使用IN)
     *
     * @param tableName    表名
     * @param updateMap    更新内容
     * @param conditionMap 条件{key1: [id1, id2], key2: [id1, id2]}
     */
    int updateWithInByCondition(@Param("tableName") String tableName, @Param("updateMap") Map updateMap, @Param("conditionMap") Map conditionMap);

    /**
     * 根据表名获取序列
     *
     * @param tableName 表名
     * @return
     */
    long sequenceNextval(@Param("tableName") String tableName);
}
