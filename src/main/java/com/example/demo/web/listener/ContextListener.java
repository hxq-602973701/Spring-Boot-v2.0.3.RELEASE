package com.example.demo.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 * @author lt
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor.
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("监听器被销毁....");
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("监听器初始化....");
    }

}