package org.jflores.project;

import org.jflores.project.models.Order;
import org.jflores.project.models.Tables;
import org.jflores.project.models.Validations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NewOrder {
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

        System.out.println("===== enter zip code   (from 4 to 5 numbers)  =====");
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
        String regularExpresion = "";
        String errorMessage = "";
        switch (value) {
            case CUSTOMER:
                regularExpresion = "^(?![\\s])[A-Za-z\\s\\p{L}']+$";
                errorMessage = "the customer's name entered:\n" +
                        "- contains numbers\n" +
                        "- has an invalid character";
                break;
            case PRODUCT:
                regularExpresion = "^(?![\\s])[\\p{all}]+";
                errorMessage = "the product name entered:\n" +
                        "- contains empty spaces";
                break;
            case QUANTITY:
                regularExpresion = "^(?![\\s])[0-9]+$";
                errorMessage = "the quantity of the product entered:\n" +
                        "- is negative\n" +
                        "- has an invalid character";
                break;
            case PRICE:
                regularExpresion = "^(?![\\s])[0-9]+(\\.[0-9]{1,2}+)?$";
                errorMessage = "the price of the product entered:\n" +
                        "- is negative\n" +
                        "- ',' were used for decimal\n" +
                        "- has an invalid character\n" +
                        "- more than 2 decimal places";
                break;
            case DISCOUNT:
                regularExpresion = "^(?![\\s])[0]+(\\.[0-9]{1,2}+)?$";
                errorMessage = "the discount of the product entered:\n" +
                        "- is not within the range of 0.0 to 0.9\n" +
                        "- ',' were used for decimal\n" +
                        "- is negative\n" +
                        "- has an invalid character\n" +
                        "- more than 2 decimal places";
                break;
            case POSTAL_CODE:
                regularExpresion = "^(?![\\s])[0-9]{4,5}+$";
                errorMessage = "the postal code entered:\n" +
                        "- is out of range\n" +
                        "- is negative\n" +
                        "- has an invalid character";
                break;
        }
        while (true) {
            if (value.equals(Validations.CUSTOMER) || value.equals(Validations.PRODUCT)) {
                data = scanner.nextLine();
            } else {
                data = scanner.next();
            }

            if (data.matches(regularExpresion)) {
                return data;
            } else {
                System.out.println("An error has occurred!  " + "DATA ENTERED: " + data + "\n" + "check that the data is not empty...\n" +
                        "or one of the following options\n" + errorMessage +
                        "\nPlease try again:");
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
