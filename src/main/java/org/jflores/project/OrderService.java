package org.jflores.project;

import org.jflores.project.exceptions.IdValueNotFoundException;
import org.jflores.project.models.Order;
import org.jflores.project.models.Tables;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static org.jflores.project.ValidationHelper.*;

public class OrderService {

    public static final String COUNTRY_US = "US";
    public static final String DASH = "-";
    public static final int MAX_QUANTITY_PRODUCT = 100;


    private Scanner scanner = new Scanner(System.in);
    private OrderDAO orderDAO = new OrderDAO();

    public void addNewOrder() {

        Order order = new Order();
        order.setOrderId(getNewOrderId());
        order.setOrderDate(getCurrentDate());

        System.out.println("===== Enter the customer's first and last name =====");
        String customerName = validateOnlyLetters(scanner.nextLine());
        order.setCustomerId(findIdValue(customerName, Tables.CUSTOMER));

        System.out.println("===== Enter product name =====");
        String productName = validateIsNotEmpty(scanner.nextLine());
        order.setProductId(findIdValue(productName, Tables.PRODUCT));

        System.out.println("===== Enter product quantity =====");

        int quantity = validateIsPositiveInteger(scanner.nextLine(),0, MAX_QUANTITY_PRODUCT);
        order.setQuantity(quantity);

        System.out.println("===== Enter product price (use '.' for decimal) =====");
        double price = validatePositiveDecimal(scanner.nextLine());
        order.setPrice(price);

        System.out.println("===== Enter product discount (use '.' for 2 decimal) (range 0.0 to 0.9)  =====");
        double discount = validatePercentage(scanner.nextLine());
        order.setDiscount(discount);

        System.out.println("===== Enter Postal code   (up to 5 digits)  =====");
        int postalCode = findAddressId(scanner.nextLine());
        order.setAddressId(postalCode);

        double total = computeTotal(price, quantity, discount);
        order.setTotal(total);
        order.setProfit(computeProfit(total));
        System.out.println("Total receivable: " + total);
        System.out.println(order);

        orderDAO.addNewOrderToDb(order);
    }

    private int findAddressId(String value) {
        int addressId;
        while (true) {
            addressId = validateIsPositiveInteger(value,MIN_VALUE_INTEGER, MAX_VALUE_POSTAL_CODE);
            if (orderDAO.addressIdExists(addressId)) {
                return addressId;
            } else {
                System.out.println("An error has occurred!\nPostal code: '" + addressId + "' not found in address table\n" +
                        TRY_AGAIN_MESSAGE);
                value = scanner.nextLine();
            }
        }
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

    private String findIdValue(String value, Tables table) {
        while (true) {
            try {
                if (table.equals(Tables.CUSTOMER)) {
                    value = validateOnlyLetters(value);
                } else {
                    value = validateIsNotEmpty(value);
                }
                return orderDAO.findIdValue(value, table);

            } catch (IdValueNotFoundException e) {
                System.out.println(TRY_AGAIN_MESSAGE);
                value = scanner.nextLine();
            }
        }
    }

    private double computeTotal(double price, int quantity, double discount) {
        double total = price * quantity;
        if (discount > 0.0) {
            total *= (1 - discount);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    private double computeProfit(double total) {
        double profit = total * 0.8;
        return Math.round(profit * 100.0) / 100.0;
    }

    public void modifyOrder() {
        System.out.println("===== Enter the order id of the order you want to modify =====");
        String orderId = findIdValue(scanner.nextLine(), Tables.ORDER);
        Order order = orderDAO.getOrderRecord(orderId);
        System.out.println(order);

        System.out.println("===== Enter the new quantity for this order =====");
        int quantity = validateIsPositiveInteger(scanner.nextLine(),MIN_VALUE_INTEGER, MAX_QUANTITY_PRODUCT);
        order.setQuantity(quantity);

        System.out.println("===== Enter the new discount for this order =====");
        double discount = validatePercentage(scanner.nextLine());
        order.setDiscount(discount);

        double total = computeTotal(order.getPrice(), order.getQuantity(), order.getDiscount());
        order.setTotal(total);
        double profit = computeProfit(total);
        order.setProfit(profit);
        System.out.println(order);

        orderDAO.modifyTableData(order);
    }

    public void deleteOrder() {
        System.out.println("===== Enter the order id of the order you want to delete =====");
        String orderId = findIdValue(scanner.nextLine(), Tables.ORDER);
        System.out.println(orderDAO.getOrderRecord(orderId));

        orderDAO.deleteOrderOfDb(orderId);
    }
}

