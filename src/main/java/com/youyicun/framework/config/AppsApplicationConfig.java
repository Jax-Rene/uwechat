package com.youyicun.framework.config;

import com.youyicun.controller.AdminController;
import com.youyicun.entity.Admin;
import com.youyicun.framework.spring.SpringContextHolder;
import com.youyicun.framework.spring.SpringContextUtils;
import com.youyicun.framework.spring.SpringPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.xml.transform.Source;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan(basePackages = {"com.youyicun"})
public class AppsApplicationConfig {
    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String passWord;

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter bean = new RequestMappingHandlerAdapter();
        bean.setMessageConverters(converters());
        return bean;
    }

    public List<HttpMessageConverter<?>> converters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8"))); //default 为ISO, 中文会乱码
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<Source>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        MappingJackson2HttpMessageConverter jconfig = new MappingJackson2HttpMessageConverter();
        jconfig.setPrettyPrint(true);
        converters.add(jconfig);
        return converters;
    }

    @Bean
    public static SpringPropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        SpringPropertyPlaceholderConfigurer configurer = new SpringPropertyPlaceholderConfigurer();
        configurer.setFileEncoding("UTF-8");
        configurer.setIgnoreResourceNotFound(true);
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setLocations(
                new Resource[]{
                        new ClassPathResource("config.properties"),
                        new ClassPathResource("database.properties")
                }
        );
        return configurer;
    }


    @Bean
    @Qualifier("springContextUtils")
    public SpringContextUtils contextUtils() {
        SpringContextUtils bean = new SpringContextUtils();
        return bean;
    }

    @Bean
    @Qualifier("springContextHolder")
    public SpringContextHolder contextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public Admin admin(){
        Admin admin = new Admin(userName,passWord);
        return admin;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppsApplicationConfig.class, args);
    }


}
