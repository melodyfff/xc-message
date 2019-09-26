package com.xinchen.middleware.message;

import com.xinchen.middleware.message.properties.MessagesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;

/**
 * {@link SpringBootServletInitializer} 类似于 {@link AbstractAnnotationConfigDispatcherServletInitializer}初始化DispatchServlet
 *
 * 将Root Context添加到{@link ServletContext}中
 */
@SpringBootApplication
@EnableConfigurationProperties(MessagesProperties.class)
public class MessageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MessageApplication.class);
    }

}
