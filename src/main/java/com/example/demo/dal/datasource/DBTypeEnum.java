package com.example.demo.dal.datasource;

/**
 * 数据库类型
 *
 * @author lt on 2016/6/2.
 * @version 1.0.0
 */
public enum DBTypeEnum {

    /**
     * Oracle数据库类型
     */
    Oracle,

    /**
     * MySQL数据库类型
     */
    MySQL,

    /**
     * SQLServer数据库类型
     */
    SQLServer;


    private DBTypeEnum() {
    }

}
