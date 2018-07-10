package com.example.demo.web;

import com.example.demo.dal.entity.main.user.User;
import com.example.demo.dal.entity.main.user.UserRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

    /**
     * 通过这个可以获取参数
     */
    @Autowired
    private Environment env;

    /**
     * redis模板  封装了常用的redis操作
     */
    @Autowired
    private StringRedisTemplate template;

    //============================两种方式（MongoRepository、MongoTemplate）========
    /**
     * MongoRepository
     */
    @Autowired
    private UserRepository mongoRepository;

    /**
     * MongoTemplate
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * elasticsearch
     */
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 整合thymeleaf
     *
     * @return
     */
    @RequestMapping(value = "/thymeleaf", method = RequestMethod.GET)
    public String hello() {
        speak();
        return "/thymeleaf/index-thymeleaf";
    }

    /**
     * 整合ftl
     *
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/ftl", method = RequestMethod.GET)
    public String hello1(Model model, Map<String, Object> map) {
        model.addAttribute("freeMaker", "freeMaker获取变量");
        speak();
        map.put("name", "Joe");
        //sex:性别，1：男；0：女；
        map.put("sex", 1);

        // 模拟数据
        List<Map<String, Object>> friends = Lists.newArrayList();
        Map<String, Object> friend = Maps.newHashMap();
        friend.put("name", "xbq");
        friend.put("age", 22);
        friends.add(friend);
        friend = Maps.newHashMap();
        friend.put("name", "July");
        friend.put("age", 18);
        friends.add(friend);
        map.put("friends", friends);
        return "index-ftl";
    }

    /**
     * 系统变量
     */
    public void speak() {
        System.out.println("=========>" + env.getProperty("zzp.name") + "========>(这是一个变量)=======");
    }


    /**
     * 测试redis设置值
     */
    @RequestMapping("/setValue")
    public void setValue() {
        if (!template.hasKey("redis")) {
            template.opsForValue().append("redis", "我是redis缓存");
            System.out.println("使用redis缓存保存数据成功");
        } else {
            template.delete("redis");
            System.out.println("key已存在");
        }
    }

    /**
     * redis获取值
     */
    @RequestMapping("/getValue")
    public void getValue() {

        if (!template.hasKey("redis")) {
            System.out.println("key不存在，请先保存数据");
        } else {
            //根据key获取缓存中的val
            String redis = template.opsForValue().get("redis");
            System.out.println("获取到缓存中的数据：redis缓存=" + redis);
        }
    }

    /**
     * 整合mongodb
     */
    @RequestMapping("/mongodb")
    public void mongodb() {
        mongoRepository.deleteAll();

        //使用了lombok方式 简化
        mongoRepository.save(new User(1, "lt", 19));
        mongoRepository.save(new User(2, "xb", 20));

        System.out.println("User found with findAll():");
        System.out.println("-------------------------------");
        for (User user : mongoRepository.findAll()) {
            System.out.println(user);
        }
        System.out.println("------------------------");
        User user = mongoTemplate.findOne(new Query(Criteria.where("id").is(1)), User.class);
        System.out.println(user);
        System.out.println("User found with findByName('name1'):");
        System.out.println("--------------------------------");
        System.out.println(mongoRepository.findByName("name1"));
    }

    /**
     * 整合es--插入索引
     */
    @RequestMapping("/es-index")
    public void testEsIndex() {

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source(jsonMap);

        ActionListener<IndexResponse> indexResponseActionListener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("=================================索引插入成功了====================================");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引插入失败了====================================");
            }
        };
        restHighLevelClient.indexAsync(indexRequest, indexResponseActionListener);
    }

    /**
     * 整合es--查看索引
     */
    @RequestMapping("/es-get")
    public void esGet() {

        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");

        ActionListener<GetResponse> getResponseActionListener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                System.out.println("=================================索引获取成功了====================================");
                System.out.println(getResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引获取失败了====================================");
            }
        };

        restHighLevelClient.getAsync(getRequest, getResponseActionListener);
    }

    /**
     * 整合es--删除索引
     */
    @RequestMapping("/es-del")
    public void esDelete() {

        DeleteRequest deleteRequest = new DeleteRequest(
                "posts",
                "doc",
                "1");

        ActionListener<DeleteResponse> deleteResponseActionListener = new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                System.out.println("=================================索引删除成功了====================================");
                System.out.println(deleteResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引删除失败了====================================");
            }
        };

        restHighLevelClient.deleteAsync(deleteRequest, deleteResponseActionListener);

    }

    /**
     * 整合es--更新索引
     */
    @RequestMapping("/es-update")
    public void esUpdate() {

        //更改
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", "daily update");
        UpdateRequest updateRequest = new UpdateRequest("posts", "doc", "1")
                .doc(jsonMap);

        ActionListener<UpdateResponse> updateResponseActionListener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                System.out.println("=================================索引更新成功了====================================");
                System.out.println(updateResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引更新失败了====================================");
            }
        };

        restHighLevelClient.updateAsync(updateRequest, updateResponseActionListener);

    }

}
