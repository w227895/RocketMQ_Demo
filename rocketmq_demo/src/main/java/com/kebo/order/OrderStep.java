package com.kebo.order;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: kb
 * @create: 2021-01-25 19:43
 **/
public class OrderStep {
    private long orderId;
    private String describe;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "orderId=" + orderId +
                ", describe='" + describe + '\'' +
                '}';
    }

    public static List<OrderStep> buildOrders() {
        List<OrderStep> orderStepList = new ArrayList<OrderStep>();

        OrderStep orderDemo01 = new OrderStep();
        orderDemo01.setOrderId(1039L);
        orderDemo01.setDescribe("创建");
        orderStepList.add(orderDemo01);

        OrderStep orderDemo02 = new OrderStep();
        orderDemo02.setOrderId(1065L);
        orderDemo02.setDescribe("创建");
        orderStepList.add(orderDemo02);

        OrderStep orderDemo03 = new OrderStep();
        orderDemo03.setOrderId(7235L);
        orderDemo03.setDescribe("创建");
        orderStepList.add(orderDemo03);


        OrderStep orderDemo04 = new OrderStep();
        orderDemo04.setDescribe("完成");
        orderDemo04.setOrderId(1039L);
        orderStepList.add(orderDemo04);

        OrderStep orderDemo05 = new OrderStep();
        orderDemo05.setOrderId(1065L);
        orderDemo05.setDescribe("完成");
        orderStepList.add(orderDemo05);

        OrderStep orderDemo06 = new OrderStep();
        orderDemo06.setOrderId(7235L);
        orderDemo06.setDescribe("完成");
        orderStepList.add(orderDemo06);
        return orderStepList;
    }
}

