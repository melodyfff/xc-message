package com.xinchen.middleware.demo;

import org.apache.kafka.common.errors.TimeoutException;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xinchen
 * @version 1.0
 * @date 14/07/2020 15:13
 */
public class KafkaConsumerProducerDemo {
    public static void main(String[] args) throws InterruptedException {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        CountDownLatch latch = new CountDownLatch(2);
        Producer producerThread = new Producer(KafkaProperties.TOPIC, isAsync, null, false, 10000, -1, latch);
        producerThread.start();

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC, "DemoConsumer", Optional.empty(), false, 10000, latch);
        consumerThread.start();


        if (!latch.await(5, TimeUnit.MINUTES)) {
            throw new TimeoutException("Timeout after 5 minutes waiting for demo producer and consumer to finish");
        }

        consumerThread.shutdown(2, TimeUnit.SECONDS);
        System.out.println("All finished!");
    }
}
