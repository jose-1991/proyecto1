package org.jflores.project.service;

import org.jflores.project.dao.OrderDAO;
import org.jflores.project.exceptions.IdValueNotFoundException;
import org.jflores.project.models.Order;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.jflores.project.helper.ValidationHelper.*;
import static org.jflores.project.models.Tables.*;

public class OrderService {

    public static final String COUNTRY_US = "US";
    public static final String DASH = "-";
    public static final int MAX_QUANTITY_PRODUCT = 100;

    private OrderDAO orderDAO = new OrderDAO();

    public Order addNewOrder(Order order) {

        validatePartialOrder(order);

        double total = computeTotal(order.getPrice(), order.getQuantity(), order.getDiscount());
        order.setTotal(total);
        order.setProfit(computeProfit(total));
        order.setOrderId(generateOrderId());
        order.setOrderDate(getCurrentDate());

        validateAddressId(order.getAddressId());
        String customerId = orderDAO.findIdValue(order.getCustomerName(), CUSTOMER);
        String productId = orderDAO.findIdValue(order.getProductName(), PRODUCT);
        order.setCustomerId(customerId);
        order.setProductId(productId);

        orderDAO.addNewOrderToDb(order);
        return order;
    }

    public void validatePartialOrder(Order order) {
        validateString("CustomerName", order.getCustomerName());
        validateString("ProductName", order.getProductName());

        validateNumber("Quantity", order.getQuantity());
        validateNumber("Price", order.getPrice());
        validateNumber("AddressId", order.getAddressId());
        validateRange(order.getDiscount());
    }

    public void validateRange(double discount) {
        if (discount < 0 || discount > 1) {
            throw new RuntimeException("Discount has to be between 0 and 1");
        }
    }

    public void validateNumber(String nameValue, double value) {
        if (value <= 0) {
            throw new RuntimeException(nameValue + " can not be 0 or less");
        }
    }
    public void validateNumber(String nameValue, int value) {
        if (value <= 0) {
            throw new NumberFormatException(nameValue + " can not be 0 or less");
        }
    }

    public String validateDateFormat(String value) {
        validateString("orderDate", value);
        if (!value.matches(DATE_FORMAT)) {
            throw new RuntimeException("orderDate does not have the correct Format (dd/mm/yyyy)");
        }
        return value;
    }

    public String validateString(String nameValue, String value) {
        if (value == null) {
            throw new RuntimeException(nameValue + " can not be null");
        }
        if (value.isEmpty()) {
            throw new RuntimeException(nameValue + " can not be empty");
        }
        return value;
    }

    public int validateAddressId(int value) {
        if (!orderDAO.addressIdExists(value)) {
            throw new IdValueNotFoundException("Id not found");
        }
        return value;
    }

    public String generateOrderId() {
        int sixDigitNumber = (int) (Math.random() * 100000) + 100000;
        return COUNTRY_US + DASH + getCurrentYear() + DASH + sixDigitNumber;
    }

    public String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        return simpleDateFormat.format(new Date());
    }

    public double computeTotal(double price, int quantity, double discount) {
        double total = price * quantity;
        if (discount > 0.0) {
            total *= (1 - discount);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public double computeProfit(double total) {
        double profit = total * 0.8;
        return Math.round(profit * 100.0) / 100.0;
    }

    public Order modifyOrder(String orderId, int newQuantity, double newDiscount) {
        Order order = orderDAO.getOrderRecord(orderId);
        System.out.println("Current Order" + order);

        order.setQuantity(newQuantity);
        order.setDiscount(newDiscount);

        double total = computeTotal(order.getPrice(), order.getQuantity(), order.getDiscount());
        order.setTotal(total);
        double profit = computeProfit(total);
        order.setProfit(profit);

        orderDAO.modifyTableData(order);
        return order;
    }

    public Order deleteOrder(String orderId) {
        Order order = orderDAO.getOrderRecord(orderId);
        orderDAO.deleteOrderOfDb(orderId);
        return order;
    }
}

