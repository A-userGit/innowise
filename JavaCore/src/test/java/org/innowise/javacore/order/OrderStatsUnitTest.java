package org.innowise.javacore.order;

import org.innowise.javacore.order.entity.Customer;
import org.innowise.javacore.order.entity.Order;
import org.innowise.javacore.order.entity.OrderItem;
import org.innowise.javacore.order.enums.Category;
import org.innowise.javacore.order.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderStatsUnitTest {

    @Test
    public void testGetUniqueCities()
    {
        List<Order> orders = getOrders(10);
        List<Customer> customers = getCustomers(10);
        for(int i = 0; i < 10; i++)
        {
            if(i%2 == 0) {
                customers.get(i).setCity("city1");
            }
            orders.get(i).setCustomer(customers.get(i));
        }
        List<String> uniqueCities = OrderStats.getUniqueCities(orders);
        assertEquals(5,uniqueCities.stream().distinct().count());
    }

    @Test
    public void testGetSumForCompleted()
    {
        List<Order> orders = getOrders(20);
        int sum = 0;
        for(Order o : orders)
        {
            int status = (int)Math.floor((Math.random() * 10) % 5);
            o.setStatus(OrderStatus.values()[status]);
            List<OrderItem> orderItems = generateItems(12);
            o.setItems(orderItems);
            if(OrderStatus.values()[status] == OrderStatus.DELIVERED)
            {
                sum = sum + 12000;
            }
        }
        double sumForCompleted = OrderStats.getSumForCompleted(orders);
        assertEquals(sum, sumForCompleted);
    }

    @Test
    public void testGetMostPopularProduct()
    {
        List<Order> orders = getOrders(20);
        for(Order o : orders)
        {
            List<OrderItem> orderItems = generateItems(12);
            orderItems.get(2).setQuantity(1000);
            o.setItems(orderItems);
        }
        String mostPopularProduct = OrderStats.getMostPopularProduct(orders);
        assertEquals("item2", mostPopularProduct);
    }

    @Test
    public void testGetMostPopularProductNoProducts()
    {
        List<Order> orders = getOrders(20);
        for(Order o : orders)
        {
            o.setItems(new ArrayList<>());
        }
        String mostPopularProduct = OrderStats.getMostPopularProduct(orders);
        assertNull(mostPopularProduct);
    }

    @Test
    public void testGetAvgCheckForDelivered()
    {
        int amount = 0;
        List<Order> orders = getOrders(20);
        for(Order o : orders)
        {
            int goodsAmount = 12;
            int status = (int)Math.floor((Math.random() * 10) % 5);
            if(OrderStatus.values()[status] == OrderStatus.DELIVERED) {
                amount++;
                goodsAmount = 5;
            }
            o.setStatus(OrderStatus.values()[status]);
            List<OrderItem> orderItems = generateItems(goodsAmount);
            o.setItems(orderItems);
        }
        double avgCheckForDelivered = OrderStats.getAvgCheckForDelivered(orders);
        assertEquals(5000, avgCheckForDelivered);
    }

    @Test
    public void testGetCustomersWithMoreThan5Orders()
    {
        List<Order> orders = getOrders(30);
        List<Customer> customers = getCustomers(30);
        for(int i = 0; i < 10; i++)
        {
            if(i%2 == 0) {
                orders.get(i).setCustomer(customers.get(23));
            }else {
                orders.get(i).setCustomer(customers.get(i));
            }
        }
        for(int i = 10; i < 20; i++)
        {
            if(i%2 == 0) {
                orders.get(i).setCustomer(customers.get(15));
            }else{
                orders.get(i).setCustomer(customers.get(i));
            }
        }
        for(int i = 20; i < 30; i++)
        {
            if(i%2 == 0) {
                orders.get(i).setCustomer(customers.get(9));
            }else{
                orders.get(i).setCustomer(customers.get(i));
            }
        }
        List<Customer> customersWithMoreThan5Orders = OrderStats.getCustomersWithMoreThan5Orders(orders);
        assertEquals(3, customersWithMoreThan5Orders.size());
        int[] id = new int[] {23,15,9};
        for(Customer c :customersWithMoreThan5Orders)
        {
            assertTrue(Arrays.stream(id).anyMatch(i-> (i + "").equals(c.getCustomerId())));
        }
    }

    private List<Order> getOrders(int amount)
    {
        ArrayList<Order> orders = new ArrayList<>();
        for(int i = 0; i <amount; i++)
        {
            Order order = new Order();
            order.setOrderId(""+i);
            order.setOrderDate(LocalDateTime.now());
            orders.add(order);
        }
        return orders;
    }

    private List<Customer> getCustomers(int amount)
    {
        ArrayList<Customer> customers = new ArrayList<>();
        for(int i = 0; i < amount; i++)
        {
            Customer customer = new Customer();
            customer.setCustomerId(""+i);
            customer.setAge(20 + i);
            customer.setName("name"+i);
            customer.setEmail("random@mail.rand");
            customer.setRegisteredAt(LocalDateTime.now());
            customer.setCity("city"+i);
            customers.add(customer);
        }
        return customers;
    }

    private List<OrderItem> generateItems(int amount)
    {
        List<OrderItem> items = new ArrayList<>();
        for(int i = 0; i < amount; i++)
        {
            OrderItem item = new OrderItem();
            item.setProductName("item"+ i);
            item.setPrice(100);
            item.setQuantity(10);
            item.setCategory(Category.BOOKS);
            items.add(item);
        }
        return items;
    }
}
