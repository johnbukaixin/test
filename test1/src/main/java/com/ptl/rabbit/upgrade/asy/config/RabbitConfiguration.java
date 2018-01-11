package com.ptl.rabbit.upgrade.asy.config;

import com.ptl.rabbit.upgrade.asy.handler.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * created by panta on 2018/1/9.
 *
 * @author panta
 */
@Configuration
public class RabbitConfiguration {

    public final static String QUEUE_NAME = "spring-boot";

//    @Autowired
//    private RabbitMessageListener listener;

    /**
     * Queues are where the messages end up and are received by consumers
     * @return
     */
    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME,false);
    }

    /**
     * 一个message包含两个部分，payload 和 label，前者相当于发送的消息，后者相当于exchange标签，告诉消息服务器该发送到哪一个consumer
     *
     * exchange:Exchanges are where producers publish their messages.
     * 有三种类型的Exchanges：direct, fanout,topic。 每个实现了不同的路由算法（routing algorithm）。
     　　Direct exchange: 如果 routing key 匹配, 那么Message就会被传递到相应的queue中。其实在queue创建时，它会自动的以queue的名字作为routing key来绑定那个exchange。
     　　Fanout exchange: 会向响应的queue广播。
     　　Topic exchange: 对key进行模式匹配，比如ab*可以传递到所有ab*的queue。
     * @return
     */
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("spring-boot-exchange");
    }


    @Bean
    SimpleMessageListenerContainer getContainer(ConnectionFactory factory, MessageListenerAdapter adapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setQueueNames(QUEUE_NAME);
        container.setConnectionFactory(factory);
        container.setMessageListener(adapter);
        return container;
    }

    @Bean
    MessageListenerAdapter adapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }

    /**
     * Bindings are how the messages get routed from the exchange to particular queues.
     * @param exchange
     * @param queue
     * @return
     */
    @Bean
    Binding builder(TopicExchange exchange, Queue queue){
        return  BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }
    @Bean
    ConnectionFactory factory(){
        CachingConnectionFactory factory = new CachingConnectionFactory("45.78.19.188");
        factory.setChannelCacheSize(5);
        factory.setPublisherReturns(true);
        factory.setPublisherConfirms(true);
        //一个链接监听机制 Publishing is Asynchronous - How to Detect Success and Failures
        factory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {

            }

            @Override
            public void onClose(Connection connection) {

            }
        });
        return  factory;
    }

    @Bean
    public AmqpTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory());


        RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {

            }
        };
        rabbitTemplate.setConfirmCallback(confirmCallback);
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        return rabbitTemplate;
    }
}
