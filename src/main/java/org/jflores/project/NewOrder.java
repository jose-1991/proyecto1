package org.jflores.project;

import org.jflores.project.models.Order;
import org.jflores.project.models.Tables;
import org.jflores.project.models.Validations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NewOrder {
    final static String TRY_AGAIN_MESSAGE = "please try again";
    static final String COUNTRY_US = "US";
    static final String SEPARATOR_ORDER_ID = "-";
    Scanner scanner = new Scanner(System.in);


    public void createNewOrder(Order order) {
        DataBase dataBase = new DataBase();
        order.setOrderId(getNewOrderId());

        System.out.println("===== enter the customer's first and last name =====");
        String customerName = validateData(Validations.CUSTOMER);
        order.setCustomerId(dataBase.findIdValue(customerName, Tables.CUSTOMER));

        System.out.println("===== enter product name =====");
        String productName = validateData(Validations.PRODUCT);
        order.setProductId(dataBase.findIdValue(productName, Tables.PRODUCT));

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
        double total = computeTotal(order.getPrice(), order.getQuantity(), order.getDiscount());
        double roundedTotal = Math.round(total * 100.0) / 100.0;
        order.setTotal(roundedTotal);
        double profit = total * 0.8;
        double roundedProfit = Math.round(profit * 100.0) / 100.0;
        order.setProfit(roundedProfit);
        System.out.println("total receivable: " + roundedTotal);

        System.out.println(order);

    }


    public String validateData(Validations value) {
        String data;
        String regularExpression = "";
        String firstErrorMessage = value.toString() + " entered contains letters";
        boolean isCustomer = false;
        boolean isProduct = false;
        boolean isQuantity = false;
        boolean isPrice = false;
        boolean isDiscount = false;
        boolean isPostalCode = false;
        switch (value) {
            case CUSTOMER:
                regularExpression = "^[A-Za-z\\s']+$";
                firstErrorMessage = "the customer name entered contains numbers";
                isCustomer = true;
                break;
            case PRODUCT:
                regularExpression = "^[\\p{all}]+$";
                isProduct = true;
                break;
            case QUANTITY:
                regularExpression = "^([-])?[0-9]+$";
                isQuantity = true;
                break;

            case PRICE:
                regularExpression = "^([-])?[0-9]+([.])?[0-9]*$";

                isPrice = true;
                break;
            case DISCOUNT:
                regularExpression = "^([-])?[0-9]+([.])?[0-9]*$";
                isDiscount = true;

                break;

            case POSTAL_CODE:
                regularExpression = "^([-])?[0-9]+$";
                isPostalCode = true;
                break;

        }
        while (true) {
            data = scanner.nextLine().trim();

            if (data.matches(regularExpression)) {
                if (!(isCustomer || isProduct)) {
                    if (data.startsWith("-")) {
                        System.out.println("Error! The " + value.toString() + " entered is negative\n" +
                                TRY_AGAIN_MESSAGE);
                        continue;
                    }
                    if (isPrice || isDiscount) {
                        if (data.matches("^[0-9]+(\\.?[0-9]{3,})+$")) {
                            System.out.println("Error! the " + value.toString() + " cannot have more than 2 decimal places\n" +
                                    TRY_AGAIN_MESSAGE);
                            continue;
                        }
                    }
                    if (isDiscount) {
                        if (!data.startsWith("0")) {
                            System.out.println("Error! the discount is out to range (from 0.0 to 0.9)\n" +
                                    TRY_AGAIN_MESSAGE);
                            continue;
                        }
                    }
                    if (isQuantity || isPrice) {
                        if (data.equals("0")) {
                            System.out.println("Error! the " + value.toString() + " cannot be 0\n" +
                                    TRY_AGAIN_MESSAGE);
                            continue;
                        }
                    }
                    if (isPostalCode) {
                        if (data.length() < 4 || data.length() > 5) {
                            System.out.println("Error! the zip code must be 4 o 5 digits\n" +
                                    TRY_AGAIN_MESSAGE);
                            continue;
                        }
                    }
                    return data;
                }
                return data;
            } else {
                if (data.isEmpty()) {
                    System.out.println("Error! the " + value.toString() + " entered is empty");
                }
                if (data.matches("^[a-zA-Z0-9\\s]+$")) {
                    System.out.println("Error! " + firstErrorMessage +
                            "\nplease try again");
                    continue;
                }
                if (data.matches("^[\\p{all}]+$")) {
                    System.out.println("Error! the " + value.toString() + " contains invalid characters");
                    if (isPrice || isDiscount) {
                        System.out.println("(use '.' up to 2 decimal places)");
                    }
                }

                System.out.println("please try again:");
            }
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
        if (discount > 0.0) {
            total *= (1 - discount);
        }
        return total;
    }
}
