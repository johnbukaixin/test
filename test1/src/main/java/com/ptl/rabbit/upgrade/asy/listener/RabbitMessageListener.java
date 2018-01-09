package com.ptl.rabbit.upgrade.asy.listener;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * created by panta on 2018/1/9.
 *
 * @author panta
 */
@Component
public class RabbitMessageListener implements org.springframework.amqp.core.MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.printf("Message:" + new String(message.getBody()));
    }
}
