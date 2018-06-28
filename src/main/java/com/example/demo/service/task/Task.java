package com.example.demo.service.task;

import com.example.demo.dal.dao.common.CommonDAO;
import com.example.demo.dal.datasource.DataSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class Task {

    /**
     * 通用dao方法
     */
    @Resource
    private CommonDAO commonDAO;

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(Task.class);

//    @Scheduled(cron = "0/1 * * * * ?")
    public void task01() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "........task01");
        TimeUnit.SECONDS.sleep(2);
    }

//    @Scheduled(cron = "0/1 * * * * ?")
    public void task02() {
        System. out.println(Thread.currentThread().getName() + "........task02");
    }

//    @Scheduled(cron = "0/1 * * * * ?")
    public void task03() {
        System.out.println(Thread.currentThread().getName() + "........task03");
    }

//        @Scheduled(cron = "0/1 * * * * ?")
    public void task04() {
            logger.info("执行common多数据源...");
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("newsId", 50);
            String tableName = "t_news";
            List<HashMap> mapList = commonDAO.selectByCondition(DataSourceEnum.SD, tableName, conditionMap, 1);

            logger.info("common多数据源配置成功...");
    }
}
