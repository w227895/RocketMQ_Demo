package com.kebo.base.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @description: 发送异步消息
 * @author: kb
 * @create: 2021-01-24 22:11
 **/
public class AsyncProducer {
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
            message.setTags("asyncTag");
            message.setBody(("Hello World" + i).getBytes());
//        5.发送异步消息
            try {
                defaultMQProducer.send(message, new SendCallback() {
                    /** 
                    * @Description: 发送成功的回调函数 
                    * @Param: [sendResult] 
                    * @return: void
                    * @Author: kb
                    * @Date: 2021/1/24
                    * @Time: 22:13
                            
                    */ 
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("发送成功"+sendResult);
                    }

                    /** 
                    * @Description: 发送失败的回调函数
                    * @Param: [throwable] 
                    * @return: void
                    * @Author: kb
                    * @Date: 2021/1/24
                    * @Time: 22:14
                            
                    */ 
                    public void onException(Throwable throwable) {
                        System.out.println("发送异常"+throwable);
                    }
                });
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

