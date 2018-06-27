package com.example.demo.dal.dao.base;

import com.example.demo.dal.entity.base.BaseEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用DAO基类,其他DAO继承该基类即可
 *
 * @author lt on 2015/2/3.
 * @version 1.0.0
 */
public interface BaseDAO<T extends BaseEntity> {

    /**
     * 只获取一条数据，当查询的结果多于一条数据时抛出异常。如果只想获取检索结果的第一条数据请一部 @see selectFirst
     *
     * @param record
     * @return
     */
    T selectOne(T record);

    /**
     * 只获取检索出来的第一条数据,忽略其他数据。
     *
     * @param record
     * @return
     */
    T selectFirst(T record);

    /**
     * 分页查询
     *
     * @param record
     * @return
     */
    PageInfo<T> selectPage(T record);  //没有实现

    /**
     * 根据条件查询返回数据列表
     *
     * @param record
     * @return
     */
    List<T> select(T record);

    /**
     * 根据条件查询返回数据条数
     *
     * @param record
     * @return
     */
    int selectCount(T record);

    /**
     * 根据主键查询
     *
     * @param pk 主键
     * @return
     */
    T selectByPrimaryKey(Object pk);

    /**
     * 插入，空属性也会插入
     *
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 插入，空属性不会插入
     *
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 插入列表，空属性不会插入
     *
     * @param recordList
     * @return
     */
    int insertListSelective(List<T> recordList); //乜有实现

    /**
     * 根据条件删除
     *
     * @param key
     * @return
     */
    int delete(T key);

    /**
     * 根据主键删除
     *
     * @param pk
     * @return
     */
    int deleteByPrimaryKey(Object pk);

    /**
     * 根据关键字逻辑删除记录（批量）
     *
     * @param ids         关键字ID数组
     * @param modifiedUid 修改者用户ID
     * @return
     */
    int deleteWithLogicByPrimaryKeys(Integer[] ids, Long modifiedUid);

    /**
     * 根据关键字逻辑删除记录（批量）
     *
     * @param ids         关键字ID数组
     * @param modifiedUid 修改者用户ID
     * @return
     */
    int deleteWithLogicByPrimaryKeys(Long[] ids, Long modifiedUid);

    /**
     * 根据主键修改，空值条件会修改成null
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据主键修改，空值条件不会修改成null
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

}
