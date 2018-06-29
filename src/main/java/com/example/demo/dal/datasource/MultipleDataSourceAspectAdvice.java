package com.example.demo.dal.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Mapper切面
 *
 * @author lt on 2017/09/16.
 * @version 1.0.0
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MultipleDataSourceAspectAdvice {

    /**
     * 切点
     */
    @Pointcut("execution(* com.example.demo.dal.mapper..*(..))")
    public void aspect() {

    }

    /**
     * 拦截切面，获取目标数据源(切面表达式请到biz-database.xml里面修改)
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("aspect()")
    public Object doAround(final ProceedingJoinPoint jp) throws Throwable {

        DataSourceType ds = null;

        Class<?>[] interfaceList = jp.getTarget().getClass().getInterfaces();
        if (interfaceList != null && interfaceList.length > 0) {
            ds = interfaceList[0].getAnnotation(DataSourceType.class);
        }

        if (MultipleDataSource.getDataSourceKey() == null) {
            if (ds == null) {
                // 默认主库
                MultipleDataSource.setDataSourceKey(DataSourceEnum.MAIN);
            } else {
                MultipleDataSource.setDataSourceKey(ds.target());
            }
        }

        try {
            //调用被拦截方法
            return jp.proceed();
        } finally {
            MultipleDataSource.setDataSourceKey(null);
        }
    }
}
