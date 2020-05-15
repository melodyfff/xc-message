package com.xinchen.middleware.message.rabbit.annotation.fanout.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author xinchen
 * @version 1.0
 * @date 26/09/2019 13:46
 */
@Component
@Slf4j
@Profile("rabbit")
@ConditionalOnProperty(prefix = "messages",name = "rabbit.way-annotation",havingValue = "true")
public class RabbitRunner implements CommandLineRunner {

    private static final int TIMES = 10;

    private final RabbitTemplate rabbitTemplate;

    public RabbitRunner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message to RabbitMQ start...");

        for (int i = 0; i < TIMES; i++) {
            // fanout扇出,会忽略routing key
            // exchange , route key , message
            rabbitTemplate.convertAndSend("xc-fanout",null,"[FANOUT] Hello From RabbitMQ!");
        }
        log.info("Sending message to RabbitMQ end...");
    }
}
