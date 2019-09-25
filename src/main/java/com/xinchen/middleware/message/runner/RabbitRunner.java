package com.xinchen.middleware.message.runner;

import com.xinchen.middleware.message.Receiver;
import com.xinchen.middleware.message.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 启动后向rabbit发送消息
 *
 * @author xinchen
 * @version 1.0
 * @date 25/09/2019 13:30
 */
@Component
@Slf4j
@Profile("rabbit")
public class RabbitRunner implements CommandLineRunner {

    private final static int TIMES = 100;

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    private final Random random = new Random();

    /**
     * 初始化
     *
     * @param rabbitTemplate  使用默认的 {@link RabbitAutoConfiguration.RabbitTemplateConfiguration#rabbitTemplate}
     * @param receiver 消息接收处理 {@link Receiver}
     */
    public RabbitRunner(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message to RabbitMQ start...");
        for (int i = 0; i < TIMES; i++) {
            // 模拟延迟
            Thread.sleep(random.nextInt(5)*1000);

            // exchange , route key , message
            rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME,"hello.world","Hello From RabbitMQ!");
        }
        log.info("Sending message to RabbitMQ end...");
    }
}
