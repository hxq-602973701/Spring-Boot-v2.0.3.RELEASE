package com.example.demo.web.filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;

public class MyFilter implements Filter {

    /**
     * 过滤器初始化
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("过滤器初始化...");
    }

    /**
     * 过滤器执行
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器执行.....");
        //这里暂时跳到下一系统过滤器，因为没做什么...
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 过滤器销毁
     */
    @Override
    public void destroy() {
        System.out.println("过滤器销毁....");
    }
}