package com.xinchen.middleware.message.rabbit.configure.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 接受到消息后进行处理的载体
 *
 * 通过{@link MessageListenerAdapter} 委托对象和处理消息的方法
 *
 * @author xinchen
 * @version 1.0
 * @date 25/09/2019 13:08
 */
@Component
@Slf4j
@ConditionalOnProperty(prefix = "messages",name = "rabbit.way-configure",havingValue = "true")
@Profile("rabbit")
public class Receiver {

    private final static String NAME = "ReceiverByConfig";

    private AtomicInteger count = new AtomicInteger(0);

    public void receiveMessage(String body){
        // 记录接收到的消息
        log.info("[√] Received[name={}] ",NAME);
        log.info("  [-] body is : {} ", body);
    }
}
