package com.ptl.rabbit.upgrade.asy.runner;

import com.ptl.rabbit.upgrade.asy.config.RabbitConfiguration;
import com.ptl.rabbit.upgrade.asy.handler.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * created by panta on 2018/1/8.
 */
@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private RabbitConfiguration configuration;

    @Autowired
    private Receiver receiver;

    @Autowired
    private ConfigurableApplicationContext context;

    public Runner() {
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.printf("Sending message！");

        ThreadFactory namedThreadFactory = new ThreadPoolTaskExecutor();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(()-> {
            for (int i = 0 ; i < 5 ; i ++){
                configuration.rabbitTemplate().convertAndSend(RabbitConfiguration.QUEUE_NAME,"Hello from RabbitMQ!");
                try {
                    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        singleThreadPool.shutdown();
        context.close();
    }
}
