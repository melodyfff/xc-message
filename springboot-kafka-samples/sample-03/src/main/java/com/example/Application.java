package com.example;

import com.common.Foo2;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author xinchen
 * @version 1.0
 * @date 18/05/2020 16:08
 */
@SpringBootApplication
public class Application {
    private final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private final static CountDownLatch LATCH = new CountDownLatch(1);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        LATCH.await();
        Thread.sleep(5_000);
        context.close();
    }

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public BatchMessagingMessageConverter batchConverter() {
        return new BatchMessagingMessageConverter(converter());
    }


    @KafkaListener(id = "fooGroup2", topics = "topic2")
    public void listen1(List<Foo2> foos) throws IOException {
        LOGGER.info("Received: " + foos);
        foos.forEach(f -> kafkaTemplate.send("topic3", f.getFoo().toUpperCase()));
        LOGGER.info("Messages sent, hit Enter to commit tx");
        // 这里发送了消息，但是事物还未提交，模拟接受一个控制台输入，结束该方法，事物正常提交
        System.in.read();
    }

    @KafkaListener(id = "fooGroup3", topics = "topic3")
    public void listen2(List<String> in) {
        // 成功接收到消息，LATCH countDown
        LOGGER.info("Received: " + in);
        LATCH.countDown();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("topic2").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("topic3").partitions(1).replicas(1).build();
    }
}
