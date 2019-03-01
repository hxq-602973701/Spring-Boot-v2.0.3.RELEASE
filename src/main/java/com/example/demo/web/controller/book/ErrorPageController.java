package com.example.demo.web.controller.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 错误页面请求配置
 */
@Controller
public class ErrorPageController {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(ErrorPageController.class);

    /**
     * 跳转到404页面
     *
     * @return
     */
    @GetMapping("/error/404")
    public String error404() {

        return "/error/404";
    }

    /**
     * 跳转到403页面
     *
     * @return
     */
    @GetMapping("/error/403")
    public String error403() {

        return "/error/403";
    }

    /**
     * 跳转到500页面
     *
     * @return
     */
    @GetMapping("/error/500")
    public String error500() {

        return "/error/500";
    }
}
