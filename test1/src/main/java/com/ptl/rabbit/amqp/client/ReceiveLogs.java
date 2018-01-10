package com.ptl.rabbit.amqp.client;

import com.rabbitmq.client.*;
import org.omg.PortableInterceptor.ServerRequestInfo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by panta on 2018/1/10.
 *
 * @author panta
 */
public class ReceiveLogs {

    private final static  String EXCHNAGE_NAME = "logs";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.242");
        factory.setUsername("admin");
        factory.setPassword("mytijian");

        Connection connection = null;
        try {
            connection = factory.newConnection();

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHNAGE_NAME,"fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName,EXCHNAGE_NAME,"");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            Consumer consumer = new DefaultConsumer(channel){

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queueName,consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }
}
