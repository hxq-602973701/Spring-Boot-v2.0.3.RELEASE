package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "/html/index";
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello1(Model model,Map<String, Object> map) {
        model.addAttribute("sda", "你好啊老铁");
        speak();
        map.put("name", "Joe");
        map.put("sex", 1);    //sex:性别，1：男；0：女；

        // 模拟数据
        List<Map<String, Object>> friends = new ArrayList<Map<String, Object>>();
        Map<String, Object> friend = new HashMap<String, Object>();
        friend.put("name", "xbq");
        friend.put("age", 22);
        friends.add(friend);
        friend = new HashMap<String, Object>();
        friend.put("name", "July");
        friend.put("age", 18);
        friends.add(friend);
        map.put("friends", friends);
        return "/index1";
    }

    public void speak() {
        System.out.println("=========>" + env.getProperty("zzp.name"));
    }
}
