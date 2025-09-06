package org.innowise.javacore.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.innowise.javacore.order.enums.Category;

@Getter
@Setter
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;
}
