package com.kebo.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: kb
 * @create: 2021-01-25 20:10
 **/
public class Consumer {
    public static void main(String[] args) {
        //        1. 创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("defaultGroup");
//        2. 指定Nameserver地址
        defaultMQPushConsumer.setNamesrvAddr("192.168.31.205:9876;192.168.31.222:9876");
//        3. 订阅主题Topic和Tag
        try {
            defaultMQPushConsumer.subscribe("base", "baseTag");
            //        4. 设置回调函数，处理消息
            defaultMQPushConsumer.registerMessageListener(new MessageListenerOrderly() {
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(Thread.currentThread().getName()+"->"+new String(list.get(i).getBody()));
                    }
                    /*try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        }

//        5. 启动消费者consumer
        try {
            defaultMQPushConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}

