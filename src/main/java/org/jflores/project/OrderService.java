package org.jflores.project;

import org.jflores.project.models.Order;
import org.jflores.project.models.Tables;
import org.jflores.project.models.Validations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static org.jflores.project.ValidationHelper.validateData;

public class OrderService {

    public static final String COUNTRY_US = "US";
    public static final String DASH = "-";
    private Scanner scanner = new Scanner(System.in);
    private OrderDAO orderDAO = new OrderDAO();


    public void addNewOrder() {
        Order order = new Order();
        order.setOrderId(getNewOrderId());

        System.out.println("===== enter the customer's first and last name =====");
        String customerName = validateData(Validations.CUSTOMER);
        order.setCustomerId(orderDAO.findIdValue(customerName, Tables.CUSTOMER));

        System.out.println("===== enter product name =====");
        String productName = validateData(Validations.PRODUCT);
        order.setProductId(orderDAO.findIdValue(productName, Tables.PRODUCT));

        System.out.println("===== enter product quantity =====");
        int quantity = Integer.parseInt(validateData(Validations.QUANTITY));
        order.setQuantity(quantity);

        System.out.println("===== enter product price (use '.' up to 2 decimal places) =====");
        double price = Double.parseDouble(validateData(Validations.PRICE));
        order.setPrice(price);

        System.out.println("===== enter product discount (use '.' up to 2 decimal places) (range 0.0 to 0.9)  =====");
        double discount = Double.parseDouble(validateData(Validations.DISCOUNT));
        order.setDiscount(discount);

        System.out.println("===== enter zip code   (from 4 to 5 digits)  =====");
        int postalCode = Integer.parseInt(validateData(Validations.POSTAL_CODE));
        order.setAddressId(postalCode);
        order.setOrderDate(getCurrentDate());
        double total = computeTotal(price, quantity, discount);
        order.setTotal(total);
        double profit = computeProfit(total);
        order.setProfit(profit);
        System.out.println("total receivable: " + total);
        System.out.println(order);
        orderDAO.addNewOrderToDb(order);
    }

    public void modifyOrder() {
        System.out.println("===== enter the order id of the order you want to modify =====");
        String orderId = validateData(Validations.ORDER_ID);
        Order order = orderDAO.getOrderRecord(orderId);
        System.out.println(order);
        System.out.println("===== enter the new quantity for this order =====");
        int quantity = Integer.parseInt(validateData(Validations.QUANTITY));
        order.setQuantity(quantity);
        System.out.println("===== enter the new discount for this order =====");
        double discount = Double.parseDouble(validateData(Validations.DISCOUNT));
        order.setDiscount(discount);
        double total = computeTotal(order.getPrice(), order.getQuantity(), order.getDiscount());
        order.setTotal(total);
        double profit = computeProfit(total);
        order.setProfit(profit);
        System.out.println(order);
        orderDAO.modifyTableData(order);
    }
    public void deleteOrder(){
        System.out.println("===== enter the order id of the order you want to delete =====");
        String orderId = validateData(Validations.ORDER_ID);
        System.out.println(orderDAO.getOrderRecord(orderId));
        orderDAO.deleteOrderOfDb(orderId);
    }

    private String getNewOrderId() {

        int fourDigitNumber = (int) (Math.random() * 100) + 2000;
        int sixDigitNumber = (int) (Math.random() * 100000) + 100000;

        return COUNTRY_US + DASH + fourDigitNumber + DASH + sixDigitNumber;
    }

    private String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        return simpleDateFormat.format(new Date());
    }

    private double computeTotal(double price, int quantity, double discount) {

        double total = price * quantity;
        if (discount > 0.0) {
            total *= (1 - discount);
        }
        total = Math.round(total * 100.0) / 100.0;
        return total;
    }

    private double computeProfit(double total) {
        double profit = total * 0.8;
        return Math.round(profit * 100.0) / 100.0;
    }
}
