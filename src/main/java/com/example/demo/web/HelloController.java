package com.example.demo.web;

import com.example.demo.dal.entity.main.user.User;
import com.example.demo.dal.entity.main.user.UserRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
     * 用户
     */
    @Autowired
    private UserRepository repository;

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
     * redis模板  封装了常用的redis操作
     */
    @Autowired
    private StringRedisTemplate template;

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
        repository.deleteAll();

        repository.save(new User(1, "lt", 19));
        repository.save(new User(2, "xb", 20));

        System.out.println("User found with findAll():");
        System.out.println("-------------------------------");
        for (User user : repository.findAll()) {
            System.out.println(user);
        }
        System.out.println("------------------------");

        System.out.println("User found with findByName('name1'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByName("name1"));
    }

}
