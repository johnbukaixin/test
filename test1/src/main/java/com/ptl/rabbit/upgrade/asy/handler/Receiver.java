package com.ptl.rabbit.upgrade.asy.handler;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * a recevier to response published message,
 * a simple POJO that defines a method for receiving messages
 * created by panta on 2018/1/8.
 */
@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message){
        System.out.println("Recevied + <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch(){
        return this.latch;
    }

}
