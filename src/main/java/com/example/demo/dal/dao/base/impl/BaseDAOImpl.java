package com.example.demo.dal.dao.base.impl;


import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.datasource.DataSourceEnum;
import com.example.demo.dal.datasource.MultipleDataSource;
import com.example.demo.dal.entity.base.BaseEntity;
import com.example.demo.dal.mapper.common.CommonMapper;
import com.example.demo.dal.utils.Ids;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 通用DAO基类,其他DAO继承该基类即可
 *
 * @author lt on 2015/2/3.
 * @version 1.0.0
 */
public abstract class BaseDAOImpl<T extends BaseEntity> implements BaseDAO<T> {

    /**
     * 日志记录器
     */
    protected static final Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);


    @Resource
    private CommonMapper commonMapper;

    /**
     * 获取Mapper
     *
     * @return
     */
    protected abstract Mapper<T> getMapper();

    /**
     * 只获取一条数据，当查询的结果多于一条数据时抛出异常。如果只想获取检索结果的第一条数据请一部 @see selectFirst
     *
     * @param record
     * @return
     */
    @Override
    public T selectOne(T record) {
        final List<T> dataList = getMapper().select(record);
        final int size = dataList.size();
        if (size == 1) {
            return dataList.get(0);
        } else if (size > 1) {
            throw new TooManyResultsException(String.format("预期获取一行数据，实际为%s行", dataList.size()));
        } else {
            return null;
        }
    }

    /**
     * 只获取检索出来的第一条数据,忽略其他数据。
     *
     * @param record
     * @return
     */
    @Override
    public T selectFirst(T record) {
        final List<T> dataList = getMapper().select(record);
        if (dataList.size() > 0) {
            return dataList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 分页查询
     *
     * @param record
     * @return
     */
    @Override
    public PageInfo<T> selectPage(T record) {
        final Set<EntityColumn> pkSet = EntityHelper.getPKColumns(record.getClass());
        final Optional<EntityColumn> firstPk = pkSet.stream().findFirst();
        // 根据主键名排序,如果存在的话。
        if (firstPk.isPresent()) {
            final String pkName = firstPk.get().getColumn();
            if (StringUtils.isNotBlank(pkName)) {
                PageHelper.orderBy(pkName);
            }
        }

        // 启用分页
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), true);
        return new PageInfo(getMapper().select(record));
    }

    /**
     * 根据关键字逻辑删除记录（批量）
     *
     * @param ids         关键字ID数组
     * @param modifiedUid 修改者用户ID
     */
    @Override
    public int deleteWithLogicByPrimaryKeys(Long[] ids, Long modifiedUid) {
        return _deleteWithLogicByPrimaryKeys(ids, modifiedUid);
    }

    @Override
    public int insertListSelective(List<T> recordList) {
        if (Ids.isNotEmpty(recordList)) {
            for (T record : recordList) {
                getMapper().insertSelective(record);
            }
        }
        return 1;
    }

    /**
     * 根据关键字逻辑删除记录（批量）(只支持主数据源)
     *
     * @param ids         关键字ID数组
     * @param modifiedUid 修改者用户ID
     */
    @Override
    public int deleteWithLogicByPrimaryKeys(Integer[] ids, Long modifiedUid) {
        return _deleteWithLogicByPrimaryKeys(ids, modifiedUid);
    }

    /**
     * 根据关键字逻辑删除记录（批量）(只支持主数据源、且必需只有一个关键字)
     *
     * @param ids         关键字ID数组
     * @param modifiedUid 修改者用户ID
     */
    private int _deleteWithLogicByPrimaryKeys(Number[] ids, Long modifiedUid) {
        Assert.notNull(ids, "ids can not be null");
        Assert.notNull(modifiedUid, "modifiedUid can not be null");

        // 设定数据源
        MultipleDataSource.setDataSourceKey(DataSourceEnum.MAIN);

        // 获取泛型类Class
        final Class<T> clazz = genericType();
        final EntityTable entityTable = EntityHelper.getEntityTable(clazz);

        // 获取泛型主键与PK
        final String table = entityTable.getName();
        final Set<EntityColumn> pkSet = entityTable.getEntityClassPKColumns();
        final int pkCount = pkSet.size();

        // 只支持单个主键的实体
        if (pkCount == 0) {
            throw new RuntimeException(String.format("实体[%s]中不存在主键字段。", clazz.getName()));
        } else if (pkCount != 1) {
            throw new RuntimeException(String.format("实体[%s]中存在多个主键字段。", clazz.getName()));
        }

        // 主键名
        final String pkName = pkSet.stream().findFirst().get().getColumn();

        // 更新字段
        final Map updateMap = Maps.newHashMap();
        updateMap.put("DEL_FLAG", true);
        updateMap.put("MODIFIED_UID", modifiedUid);
        updateMap.put("MODIFIED_TIME", new Date());

        // 条件字段
        final Map conditionMap = Maps.newHashMap();
        conditionMap.put(pkName, ids);

        // 更新
        return commonMapper.updateWithInByCondition(table, updateMap, conditionMap);
    }

    /**
     * 根据条件查询返回数据列表
     *
     * @param record
     * @return
     */
    @Override
    public List<T> select(T record) {
        return getMapper().select(record);
    }

    /**
     * 根据条件查询返回数据条数
     *
     * @param record
     * @return
     */
    @Override
    public int selectCount(T record) {
        return getMapper().selectCount(record);
    }

    /**
     * 根据主键查询
     *
     * @param pk 主键
     * @return
     */
    @Override
    public T selectByPrimaryKey(Object pk) {
        if (pk == null) {
            return null;
        }
        return getMapper().selectByPrimaryKey(pk);
    }

    /**
     * 插入，空属性也会插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(T record) {
        return getMapper().insert(record);
    }

    /**
     * 插入，空属性不会插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(T record) {

        return getMapper().insertSelective(record);
    }


    /**
     * 根据条件删除
     *
     * @param key
     * @return
     */
    @Override
    public int delete(T key) {
        return getMapper().delete(key);
    }

    /**
     * 根据主键删除
     *
     * @param pk
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Object pk) {
        return getMapper().deleteByPrimaryKey(pk);
    }


    /**
     * 根据主键修改，空值条件会修改成null
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(T record) {

        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据主键修改，空值条件不会修改成null
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(T record) {

        return getMapper().updateByPrimaryKeySelective(record);
    }

    /**
     * 获取泛型类Class
     *
     * @return
     */
    private Class<T> genericType() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<T>) params[0];
    }

}
