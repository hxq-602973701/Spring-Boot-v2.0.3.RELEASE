package com.example.demo.dal.datasource;

/**
 * 数据源<br> 添加或修改数据源时，请一并修改[biz-database.xml]里的配置。
 *
 * @author lt on 2017/09/16.
 * @version 1.0.0
 */
public enum DataSourceEnum {

    /**
     * 主数据源（mysql）
     */
    MAIN(DBTypeEnum.MySQL),

    SD(DBTypeEnum.MySQL),

    ES(DBTypeEnum.Es);

    /**
     * 数据库类型
     */
    private final DBTypeEnum type;

    DataSourceEnum(DBTypeEnum type) {
        this.type = type;
    }

    public DBTypeEnum getValue() {
        return this.type;
    }


}
