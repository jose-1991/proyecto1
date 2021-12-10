package org.jflores.proyect.modelos;

import java.util.Objects;

public class Order {
    private String order_ID;
    private String customer_ID;
    private int address_ID;
    private String product_ID;
    private String orderDate;
    private Double sales;
    private int quantity;
    private Double discount;
    private Double total;
    private Double profit;



    public String getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(String order_ID) {
        this.order_ID = order_ID;
    }

    public String getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        this.customer_ID = customer_ID;
    }

    public int getAddress_ID() {
        return address_ID;
    }

    public void setAddress_ID(int address_ID) {
        this.address_ID = address_ID;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        this.sales = sales;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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


    @Override
    public String toString() {
        return  order_ID + " ] " +
                orderDate  + " | " +
                customer_ID  + " | " +
                address_ID + " | " +
                product_ID + " | " +
                sales + " | " +
                quantity + " | " +
                discount + " | " +
                total + " | "+
                profit + "\n";
    }
}
