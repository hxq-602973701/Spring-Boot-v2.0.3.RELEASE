//package com.example.demo.dal.datasouce;
//
///**
// * 保存一个线程安全的DatabaseType容器
// *
// * @author lt
// * @date 2018/06/27
// */
//public class DataBaseContextHolder {
//
//    /**
//     * 用于存放多线程环境下的成员变量
//     */
//    private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<>();
//
//    public static void setDatabaseType(DataBaseType type) {
//        contextHolder.set(type);
//    }
//
//    public static DataBaseType getDatabaseType() {
//        return contextHolder.get();
//    }
//}