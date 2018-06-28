//package com.example.demo.dal.datasouce;
//
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.stereotype.Component;
//
//import java.utils.List;
//
///**
// * 动态处理数据源，根据命名区分
// *
// * @author lt
// * @date 2018/06/27
// */
//@Aspect
//@Component
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//public class DataSourceAspect {
//
//
//    private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
//
//    @Pointcut("execution(* com.example.demo.dal.mapper..*(..))")
//    public void aspect() {
//
//    }
//
//    @Before("aspect()")
//    public void before(JoinPoint point) {
//        String className = point.getTarget().getClass().getName();
//        String method = point.getSignature().getName();
//        String args = StringUtils.join(point.getArgs(), ",");
//        logger.info("className:{}, method:{}, args:{} ", className, method, args);
//        try {
//            for (DataBaseType type : DataBaseType.values()) {
//                List<String> values = DynamicDataSource.METHOD_TYPE_MAP.get(type);
//                for (String key : values) {
//                    if (method.startsWith(key)) {
//                        logger.info(">>{} 方法使用的数据源为:{}<<", method, key);
//                        DataBaseContextHolder.setDatabaseType(type);
//                        DataBaseType types = DataBaseContextHolder.getDatabaseType();
//                        logger.info(">>{}方法使用的数据源为:{}<<", method, types);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//    }
//}