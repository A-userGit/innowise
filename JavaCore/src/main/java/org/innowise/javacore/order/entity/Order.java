package org.innowise.javacore.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.innowise.javacore.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Order {
    private String orderId;
    private LocalDateTime orderDate;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;
}
