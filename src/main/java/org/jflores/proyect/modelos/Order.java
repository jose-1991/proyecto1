package org.jflores.proyect.modelos;

import java.util.Objects;

public class Order {
    private int id;
    private int customer_ID;
    private int product_ID;
    private String orderDate;
    private int quantity;
    private Double total;
    private Double profit;

    private Customer customer;
    private Product product;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customer, order.getCustomer()) && Objects.equals(product, order.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, product);
    }

    @Override
    public String toString() {
        return  id + ") " +
                customer_ID  + " | " +
                product_ID + " | " +
                orderDate + " | " +
                quantity + " | " +
                total + " | "+
                profit;
    }
}
