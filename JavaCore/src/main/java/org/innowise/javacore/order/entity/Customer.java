package org.innowise.javacore.order.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;
}
