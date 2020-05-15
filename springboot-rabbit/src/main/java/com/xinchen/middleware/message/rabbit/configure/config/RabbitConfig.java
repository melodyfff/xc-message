package com.xinchen.middleware.message.rabbit.configure.config;

import com.xinchen.middleware.message.rabbit.configure.receiver.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 *
 * Rabbit Config
 *

 *
 * @author xinchen
 * @version 1.0
 * @date 25/09/2019 11:38
 */
@Configuration
@Profile("rabbit")
@ConditionalOnProperty(prefix = "messages",name = "rabbit.way-configure",havingValue = "true")
public class RabbitConfig {

    public static final String TOPIC_EXCHANGE_NAME = "xc-exchange";

    public static final String QUEUE_NAME = "xc-queue";

    @Bean
    Queue queue(){
        // 创建新的queue, 设置持久化durable为false
        return new Queue(QUEUE_NAME, false);
    }


    /**
     * exchange有4个类型：direct，topic，fanout，header
     *
     * {@link DirectExchange} 直接, 在使用这个类型的Exchange时，可以不必指定routing key的名字，在此类型下创建的Queue有一个默认的routing key，这个routing key一般同Queue同名, Exchange要求routing key要完全相同
     *
     * {@link FanoutExchange} 扇出, 使用这种类型的Exchange，会忽略routing key的存在，直接将message广播到所有的Queue中
     *
     * {@link TopicExchange} 主题, Topic Exchange是根据routing key和Exchange的类型将message发送到一个或者多个Queue中,routing key可以通过通配符匹配
     *
     * {@link HeadersExchange} 标题, Headers Exchange不同于上面三种Exchange，它是根据Message的一些头部信息来分发过滤Message，忽略routing key的属性
     *
     * {@link CustomExchange} 自定义
     *
     * @return TopicExchange
     */
    @Bean
    TopicExchange topicExchange(){
        // 配置exchange, 采用topic主题发布
        // 默认durable=true , autoDelete=false
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue,TopicExchange exchange){
        // 绑定queue,exchange和routingKey
        // 关于routingKey: '*' 匹配单个单词，'#' 匹配多个单词
        return BindingBuilder.bind(queue).to(exchange).with("hello.#");
    }

    /**
     * 消息监听
     *
     * 将会创建和这个beanName相同的线程池接收处理消息
     *
     * @param connectionFactory ConnectionFactory {@link RabbitAutoConfiguration} 中自动注入
     * @param messageListenerAdapter listenerAdapter
     * @return SimpleMessageListenerContainer
     */
    @Bean("xc-rabbit")
    SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory,
                                                                  MessageListenerAdapter messageListenerAdapter){
        // 创建消息监听容器
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        // 设置rabbit客户端连接 {@link com.rabbitmq.client.Connection Connections}
        container.setConnectionFactory(connectionFactory);

        // 设置监听的队列名
        container.setQueueNames(QUEUE_NAME);

        // 设置接受到消息后的后续处理
        container.setMessageListener(messageListenerAdapter);

        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter(Receiver receiver){
        // 为传入的对象构建委托delegate, 当接收到消息通过委托调用处理消息的方法
        // 默认代理的方法名为: handleMessage()
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
