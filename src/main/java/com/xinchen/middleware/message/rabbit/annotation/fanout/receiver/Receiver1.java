package com.xinchen.middleware.message.rabbit.annotation.fanout.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * 注解式receiver
 *
 * @author xinchen
 * @version 1.0
 * @date 26/09/2019 10:30
 */
@RabbitListener(
        group = "xc-fanout",
        bindings = @QueueBinding(value = @Queue(value ="fanout-queue",durable = "false"),
                exchange = @Exchange(value = "xc-fanout",type = ExchangeTypes.FANOUT))

)
@Slf4j
@Component
@Profile("rabbit")
@ConditionalOnProperty(prefix = "messages",name = "rabbit.way-annotation",havingValue = "true")
public class Receiver1 {

    private static final String NAME = "Receiver1";

    @RabbitHandler
    public void process(String o, Channel channel, Message message) throws IOException {
        // Acknowledge 消息已经消费, 队列中可以删除
        log.info("[√] Received[name={}] ",NAME);
        log.info("  [-] Message is : {} ", message);
        log.info("  [-] Channel is : {} ", channel);
        log.info("  [-] body is : {} ", o);
    }
}
