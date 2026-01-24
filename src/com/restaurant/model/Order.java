package com.restaurant.model;

import java.util.Date;

public class Order {
    private int id;
    private String customerName;
    private int quantity;
    private Date orderDate;

    private MenuItem menuItem;

    public Order(int id, String customerName, MenuItem menuItem, int quantity, Date orderDate) {
        this.id = id;
        this.customerName = customerName;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    // Getters
    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public Date getOrderDate() { return orderDate; }
}