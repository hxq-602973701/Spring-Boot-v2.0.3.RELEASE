package com.example.demo.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
}
