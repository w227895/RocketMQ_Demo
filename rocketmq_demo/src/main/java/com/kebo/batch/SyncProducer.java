package com.kebo.batch;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 发送批量消息
 * @author: kb
 * @create: 2021-01-24 20:54
 **/
public class SyncProducer {
    public static void main(String[] args) throws Exception {
//        1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("defaultGroup");
//        2.指定Nameserver地址
        defaultMQProducer.setNamesrvAddr("192.168.31.205:9876;192.168.31.222:9876");
//        3.启动producer
        defaultMQProducer.start();

        List<Message> messageList = new ArrayList<Message>();
//        4.创建消息对象，指定主题Topic、Tag和消息体
        Message message1 = new Message("base","Tag1",("Hello World"+1).getBytes());
        Message message2 = new Message("base","Tag1",("Hello World"+2).getBytes());
        Message message3 = new Message("base","Tag1",("Hello World"+3).getBytes());
        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);
//        5.发送批量消息
        SendResult result = defaultMQProducer.send(messageList);
        System.out.println(result);
//        6.关闭生产者producer
        defaultMQProducer.shutdown();
    }
}

