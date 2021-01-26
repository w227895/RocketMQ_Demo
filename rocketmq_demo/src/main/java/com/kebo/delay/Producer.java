package com.kebo.delay;

import com.kebo.order.OrderStep;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: kb
 * @create: 2021-01-25 21:15
 **/
public class Producer {
    public static void main(String[] args) throws Exception {
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
            message.setTopic("delayBase");
            message.setTags("baseTag");
            message.setBody(orderStepList.get(i).toString().getBytes());
            //设定延迟时间
            //message.setDelayTimeLevel(2);
//        5.发送消息
            SendResult result = defaultMQProducer.send(message);
            System.out.println(result);
        }

        TimeUnit.SECONDS.sleep(1);

//        6.关闭生产者producer
        defaultMQProducer.shutdown();
    }
}

