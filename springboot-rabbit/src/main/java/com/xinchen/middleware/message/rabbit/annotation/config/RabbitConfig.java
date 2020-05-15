package com.xinchen.middleware.message.rabbit.annotation.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * {@link EnableRabbit} 开启对 {@link RabbitListener} {@link RabbitHandler} 注解的检查, 参考: https://www.jianshu.com/p/4f3281cd97ab
 *
 * 经过测试不开启@EnableRabbit时也能生效
 *
 * @author xinchen
 * @version 1.0
 * @date 26/09/2019 13:42
 */
@Configuration
@EnableRabbit
@Profile("rabbit")
@ConditionalOnProperty(prefix = "messages",name = "rabbit.way-annotation",havingValue = "true")
public class RabbitConfig {
    @Bean
    Queue xcQueue(){
        // 名字 'xc-queue' , durable : false , exclusive: false , autoDelete: true
        return new Queue("fanout-queue",false,false,true);
    }
}
