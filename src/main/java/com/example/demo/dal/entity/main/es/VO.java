package com.example.demo.dal.entity.main.es;

import java.util.HashMap;
import java.util.Map;

public class VO {
    private int id;
    private Map<String, Object> attributes = new HashMap<String, Object>();


    public VO() {
    }

    public VO(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "VO [id=" + id + ", attributes=" + attributes + "]";
    }
}