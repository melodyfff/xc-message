package com.xinchen.middleware.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

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
public class Receiver {
    /** 模拟接收多少条 */
    private static final int N = 10;

    /** 发出信号表示已经收到消息 */
    private CountDownLatch latch = new CountDownLatch(N);


    public void receiveMessage(String message){
        // 记录接收到的消息
        log.info("[√] Received <{}>", message);
        latch.countDown();
    }

    public CountDownLatch getLatch(){
        return latch;
    }
}
