package com.example.demo.web.controller.book;

import com.example.demo.dal.shiro.AjaxResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * shiro页面跳转
 */
@Controller
public class ShiroController {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(ShiroController.class);

    /**
     * 跳转到login界面
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    /**
     * 默认调跳转页
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/index1")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        return "index";
    }

    /**
     * 登录方法
     * TODO 获取不到前端传来的Boolean值 而且boolean值不是以is开头
     *
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @PostMapping("/login")
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            logger.info("登陆成功");
            return AjaxResult.success();
        } catch (AuthenticationException e) {
            logger.error("登陆失败");
        }
        return AjaxResult.error();
    }
}
