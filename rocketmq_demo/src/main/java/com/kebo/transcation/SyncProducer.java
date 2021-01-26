package com.kebo.transcation;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @description: 发送批量消息
 * @author: kb
 * @create: 2021-01-24 20:54
 **/
public class SyncProducer {
    public static void main(String[] args) throws Exception {
//        1.创建消息生产者producer，并制定生产者组名
        TransactionMQProducer defaultMQProducer = new TransactionMQProducer("defaultGroup2");
//        2.指定Nameserver地址
        defaultMQProducer.setNamesrvAddr("192.168.31.205:9876;192.168.31.222:9876");
        defaultMQProducer.setTransactionListener(new TransactionListener() {
            /**
             * @Description: 在该方法中执行本地事务
             * @Param: [message, o]
             * @return: org.apache.rocketmq.client.producer.LocalTransactionState
             * @Author: kb
             * @Date: 2021/1/26
             * @Time: 20:29

             */
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (StringUtils.equals("tag1", message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("tag2", message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } /*else if (StringUtils.equals("tag3", message.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }*/
                return LocalTransactionState.UNKNOW;
            }

            /**
             * @Description: 该方法是MQ进行事务状态回查
             * @Param: [messageExt]
             * @return: org.apache.rocketmq.client.producer.LocalTransactionState
             * @Author: kb
             * @Date: 2021/1/26
             * @Time: 20:29

             */
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("消息的Tag:" + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
//        3.启动producer
        defaultMQProducer.start();

        String tags[] = {"tag1", "tag2", "tag3"};
        for (int i = 0; i < 3; i++) {
//        4.创建消息对象，指定主题Topic、Tag和消息体
            Message message = new Message("base", tags[i], ("Hello World" + i).getBytes());
//        5.发送事务消息
            SendResult result = defaultMQProducer.sendMessageInTransaction(message, null);
            System.out.println(result);
        }
//        6.关闭生产者producer
        defaultMQProducer.shutdown();
    }
}

