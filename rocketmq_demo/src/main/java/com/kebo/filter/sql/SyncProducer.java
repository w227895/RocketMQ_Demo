package com.kebo.filter.sql;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

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

        for (int i = 0; i < 10; i++) {
//        4.创建消息对象，指定主题Topic、Tag和消息体
            Message message = new Message("base", "Tag2", ("Hello World" + i).getBytes());
            message.putUserProperty("i", String.valueOf(i));
//        5.发送批量消息
            SendResult result = defaultMQProducer.send(message);
            System.out.println(result);
        }
//        6.关闭生产者producer
        defaultMQProducer.shutdown();
    }
}

