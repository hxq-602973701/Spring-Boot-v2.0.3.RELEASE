package com.example.demo.dal.entity.base;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.io.Serializable;

public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 5022411657026750570L;

    @JsonIgnore
    @Transient
    private int pageNum = 1;

    @JsonIgnore
    @Transient
    private int pageSize = 10;

    /**
     * 查询条件
     */
    @Transient
    private String query;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
