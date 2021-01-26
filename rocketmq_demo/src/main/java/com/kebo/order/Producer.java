package com.kebo.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: kb
 * @create: 2021-01-25 19:50
 **/
public class Producer {
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
        List<OrderStep> orderStepList = OrderStep.buildOrders();
        for (int i = 0; i < orderStepList.size(); i++) {
            Message message = new Message();
            message.setTopic("base");
            message.setTags("baseTag");
            //message.setKeys("i+" + i);
            message.setBody(orderStepList.get(i).toString().getBytes());
//        5.发送消息
            try {
                /*
                参数一:消息对象
                参数二:消息队列的选择器
                参数三:选择队列的业务标识 (订单ID)
                 */
                SendResult result = defaultMQProducer.send(message, new MessageQueueSelector() {
                    /**
                     * @Description:
                     * @Param: [list消息队列, message消息对象, 业务表示的参数]
                     * @return: org.apache.rocketmq.common.message.MessageQueue
                     * @Author: kb
                     * @Date: 2021/1/25
                     * @Time: 19:58

                     */
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        long orderId = Long.valueOf(String.valueOf(o)).longValue();
                        long index = orderId % list.size();
                        return list.get((int) index);
                    }

                }, orderStepList.get(i).getOrderId());
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        6.关闭生产者producer
        defaultMQProducer.shutdown();
    }
}

