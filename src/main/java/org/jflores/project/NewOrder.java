package org.jflores.project;

import org.jflores.project.models.Order;
import org.jflores.project.models.Tables;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NewOrder {
    static final String COUNTRY_US = "US";
    static final String SEPARATOR_ORDER_ID = "-";


    public void createNewOrder(Order order) {
        try {
            DataBase dataBase = new DataBase();
            Scanner scanner = new Scanner(System.in);
            order.setOrderId(getNewOrderId());
            System.out.println("===== enter the customer's first and last name =====");
            String customerName = scanner.nextLine();
            order.setCustomerId(dataBase.findIdValue(customerName, Tables.CUSTOMER));
            System.out.println("===== enter product name =====");
            String productName = scanner.nextLine();
            order.setProductId(dataBase.findIdValue(productName, Tables.PRODUCT));
            System.out.println("===== enter product quantity =====");
            order.setQuantity(validateInt());
            System.out.println("===== enter product price (use ',' for decimal) =====");
            order.setPrice(validatePrice());
            System.out.println("===== enter product discount (use ',' for decimal) =====");
            order.setDiscount(validateDiscount());
            System.out.println("===== enter zip code =====");
            int postalCode = scanner.nextInt();
            dataBase.findIdValue(String.valueOf(postalCode), Tables.ADDRESS);
            order.setOrderDate(getCurrentDate());
            double total = computeTotal(order.getPrice(), order.getQuantity(), order.getDiscount());
            double roundedTotal = Math.round(total * 100.0) / 100.0;
            order.setTotal(roundedTotal);
            double profit = total * 0.8;
            double roundedProfit = Math.round(profit * 100.0) / 100.0;
            order.setProfit(roundedProfit);

            System.out.println(order);
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

    public String getNewOrderId() {
        StringBuilder number = new StringBuilder();
        String orderId = COUNTRY_US;
        int random = (int) (Math.random() * 100) + 2000;
        orderId += SEPARATOR_ORDER_ID + random;
        random = (int) (Math.random() * 100000);
        number.append(random);
        for (int i = number.length(); i < 5; i++) {
            number.insert(0, 0);
        }
        orderId += SEPARATOR_ORDER_ID + number;

        return orderId;
    }

    public String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        return simpleDateFormat.format(new Date());
    }

    public double computeTotal(double price, int quantity, double discount) {
        double total;
        total = price * quantity;
        if (discount > 0.0 && discount < 1) {
            total *= (discount - 1);
        }
        return total;
    }

    public int validateInt() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                scanner.next();
                System.out.println("error when entering number...\nplease enter a whole number");
            }
        }
    }

    public double validateDiscount() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextDouble()) {
                double number = scanner.nextDouble();
                if (number >= 0.0 && number < 1.0) {
                    return number;
                } else {
                    System.out.println("enter a number between 0.0 to 0.9");
                }
            } else {
                scanner.next();
                System.out.println("error when entering number...\nplease use ´,´ for decimal");
            }
        }
    }

    public double validatePrice() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                scanner.next();
                System.out.println("error when entering number...\nplease use ´,´ for decimal");
            }
        }
    }
}
