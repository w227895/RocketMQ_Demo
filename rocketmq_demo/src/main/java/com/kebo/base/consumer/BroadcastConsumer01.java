package com.kebo.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @description: 消息接收者(负载均衡模式)
 * @author: kb
 * @create: 2021-01-25 19:09
 **/
public class BroadcastConsumer01 {
    public static void main(String[] args) {
//        1. 创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("defaultGroup");
//        2. 指定Nameserver地址
        defaultMQPushConsumer.setNamesrvAddr("192.168.31.205:9876;192.168.31.222:9876");
//        3. 订阅主题Topic和Tag
        try {
            defaultMQPushConsumer.subscribe("base", "baseTag");
            //设置消费模式为广播模式,默认为负载均衡
            defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);
            //        4. 设置回调函数，处理消息
            defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                //接受消息内容
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (int i = 0; i <list.size() ; i++) {
                        System.out.println(new String(list.get(i).getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
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

