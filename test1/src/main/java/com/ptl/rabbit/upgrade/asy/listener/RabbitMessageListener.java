package com.ptl.rabbit.upgrade.asy.listener;

import org.springframework.amqp.core.Message;

/**
 * created by panta on 2018/1/9.
 *
 * @author panta
 */

public class RabbitMessageListener implements org.springframework.amqp.core.MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("Message:" + new String(message.getBody()));
    }
}
