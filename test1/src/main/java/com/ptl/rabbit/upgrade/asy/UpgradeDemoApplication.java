package com.ptl.rabbit.upgrade.asy;

import com.ptl.rabbit.upgrade.asy.handler.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * The bean defined in the listenerAdapter() method is registered as a message listener
 * in the container defined in container().
 * It will listen for messages on the "spring-boot" queue.
 * Because the Receiver class is a POJO,
 * it needs to be wrapped in the MessageListenerAdapter,
 * where you specify it to invoke receiveMessage
 *
 * The queue() method creates an AMQP queue.
 * The exchange() method creates a topic exchange.
 * The binding() method binds these two together,
 * defining the behavior that occurs when RabbitTemplate publishes to an exchange.
 *
 * created by panta on 2018/1/5.
 * @author panta
 */
@SpringBootApplication
public class UpgradeDemoApplication {



    public static void main(String[] args) {
        SpringApplication.run(UpgradeDemoApplication.class,args);
    }
}
