package com.ptl.rabbit.amqp.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by panta on 2018/1/10.
 *
 * @author panta
 */
public class EmitLog {

    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.242");
        factory.setUsername("admin");
        factory.setPassword("mytijian");
        Connection connection;
        Channel channel;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            //Actively declare a non-autodelete, non-durable exchange with no extra arguments
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            String message = getMessage(args);
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            channel.close();
//            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }

    private static String getMessage(String[] args){
        if (args.length < 1){
            return "info: Hello World!";
        }
        return joinStrings(args, " ");
    }

    private static String joinStrings(String args[],String var){
        int length = args.length;
        if (length == 0){
            return "";
        }
        StringBuilder words = new StringBuilder(args[0]);
        for (int i = 1; i < length; i++) {
            words.append(var).append(args[i]);
        }
        return words.toString();
    }
}
