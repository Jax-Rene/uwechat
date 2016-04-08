/**
 * @(#)PortalContextLoaderListener.java 2011-9-26
 * <p/>
 * Copyright 2000-2011 by ChinanetCenter Corporation.
 * <p/>
 * All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * ChinanetCenter Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinanetCenter.
 */

package com.youyicun.framework.spring;
import com.youyicun.framework.config.AppsApplicationConfig;
import com.youyicun.framework.config.SpringWebConfig;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;
import java.beans.Introspector;


/**
 * portal启动及挂起监听器
 */
public class SpringContextLoaderListener extends ContextLoaderListener {
    private static AnnotationConfigWebApplicationContext rootContext;

    static {
        rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppsApplicationConfig.class, SpringWebConfig.class);
    }

    public SpringContextLoaderListener() {
        super(rootContext);
    }

    public SpringContextLoaderListener(WebApplicationContext context) {
        super(context);
    }


    @Override
    public void contextInitialized(ServletContextEvent event) {


        CachedIntrospectionResults.acceptClassLoader(Thread.currentThread().getContextClassLoader());

        // 启动spring

        super.contextInitialized(event);

        configDispatcherServlet(event.getServletContext());
    }

    private void configDispatcherServlet(ServletContext servletContext){
        DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);//找不到handler则抛出异常, 统一处理

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setAsyncSupported(true);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        super.contextDestroyed(event);

        CachedIntrospectionResults.clearClassLoader(Thread.currentThread().getContextClassLoader());
        Introspector.flushCaches();
    }

}
