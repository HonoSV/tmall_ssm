package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    void add(OrderItem orderItem);
    void delete(int id);
    OrderItem get(int id);
    void update(OrderItem orderItem);
    List<OrderItem> list();
    void fill(Order order);
    void fill(List<Order> os);
    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);
}
