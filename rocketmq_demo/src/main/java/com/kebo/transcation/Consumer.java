package com.kebo.transcation;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @description: 消息接收者
 * @author: kb
 * @create: 2021-01-25 19:09
 **/
public class Consumer {
    public static void main(String[] args) throws Exception{
//        1. 创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("defaultGroup2");
//        2. 指定Nameserver地址
        defaultMQPushConsumer.setNamesrvAddr("192.168.31.205:9876;192.168.31.222:9876");
//        3. 订阅主题Topic和Tag

        //defaultMQPushConsumer.subscribe("base", "Tag1||Tag2");
        defaultMQPushConsumer.subscribe("base", "*");
        //        4. 设置回调函数，处理消息
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            //接受消息内容
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(new String(list.get(i).getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
//        5. 启动消费者consumer

        defaultMQPushConsumer.start();

    }
}

