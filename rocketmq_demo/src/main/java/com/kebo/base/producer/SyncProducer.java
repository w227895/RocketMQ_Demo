package com.kebo.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @description: 发送同步消息
 * @author: kb
 * @create: 2021-01-24 20:54
 **/
public class SyncProducer {
    public static void main(String[] args) {
//        1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("defaultGroup");
//        2.指定Nameserver地址
        defaultMQProducer.setNamesrvAddr("192.168.31.205:9876;192.168.31.222:9876");
//        3.启动producer
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
//        4.创建消息对象，指定主题Topic、Tag和消息体
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("base");
            message.setTags("baseTag");
            message.setBody(("Hello World"+i).getBytes());
//        5.发送消息
            try {
                SendResult result = defaultMQProducer.send(message);
                SendStatus sendStatus = result.getSendStatus();
                String msgId = result.getMsgId();
                int queueId = result.getMessageQueue().getQueueId();
                System.out.println(sendStatus);
                System.out.println(msgId);
                System.out.println(queueId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        6.关闭生产者producer
        defaultMQProducer.shutdown();
    }
}

