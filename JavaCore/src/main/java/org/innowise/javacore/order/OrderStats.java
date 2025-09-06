package org.innowise.javacore.order;

import org.innowise.javacore.order.entity.Customer;
import org.innowise.javacore.order.entity.Order;
import org.innowise.javacore.order.entity.OrderItem;
import org.innowise.javacore.order.enums.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class OrderStats {

    public static List<String> getUniqueCities(List<Order> orders)
    {
        return orders.stream()
                .map(o -> o.getCustomer().getCity())
                .distinct().toList();
    }

    public static double getSumForCompleted(List<Order> orders)
    {
        return orders.stream().filter(o->o.getStatus()== OrderStatus.DELIVERED)
                .mapToDouble(o->o.getItems().stream()
                        .mapToDouble(i->i.getPrice()*i.getQuantity()).sum()).sum();
    }

    public static String getMostPopularProduct(List<Order> orders)
    {
        Optional<Map.Entry<String, IntSummaryStatistics>> entry = orders.stream().flatMap(order -> order.getItems().stream())
                .collect(groupingBy(OrderItem::getProductName,
                        Collectors.summarizingInt(OrderItem::getQuantity)))
                .entrySet().stream().max(Comparator.comparing(e -> e.getValue().getSum()));
        return entry.map(Map.Entry::getKey).orElse(null);
    }

    public static double getAvgCheckForDelivered(List<Order> orders)
    {
        long count = orders.stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .count();
        if(count == 0)
            return 0;
        double sum = orders.stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .flatMap(o -> o.getItems().stream())
                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        return sum/count;
    }

    public static List<Customer> getCustomersWithMoreThan5Orders(List<Order> orders)
    {
        return orders.stream().collect(groupingBy(Order::getCustomer,Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()>5)
                .map(Map.Entry::getKey).toList();
    }
}
