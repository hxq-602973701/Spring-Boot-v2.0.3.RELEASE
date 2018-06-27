//package com.example.demo.web.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//
//@Controller
//public class HelloController {
//
//
//    @Autowired
//    private Environment env;
//
//    @RequestMapping(value = "/hello",method = RequestMethod.GET)
//    public String hello() {
//        return "/html/index";
//    }
//
//    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
//    public String hello1(Model model) {
//        model.addAttribute("sda","dsasadsa");
//        speak();
//        return "/ftl/index1";
//    }
//
//    public void speak() {
//        System.out.println("=========>" + env.getProperty("zzp.name"));
//    }
//}
