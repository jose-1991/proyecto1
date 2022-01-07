package org.jflores.project;

import org.jflores.project.models.Validations;

import java.util.Scanner;

public class ValidationHelper {
    public final static String TRY_AGAIN_MESSAGE = "please try again";
    public static final String ONLY_LETTERS = "^[A-Za-z\\s']+$";
    public static final String ALL_CHARACTERS= "^[\\p{all}]+$";
    public static final String ONLY_WHOLE_NUMBERS = "^([-])?[0-9]+$";
    public static final String ONLY_DECIMAL_NUMBERS = "^([-])?[0-9]+([.])?[0-9]*$";
    public static final String FORMAT_ORDER_ID ="^(US-)[0-9]{4}([-])?[0-9]{6}$";
    public static final String NUMBERS_LETTERS = "^[a-zA-Z0-9\\s]+$";
    public static final String ONLY_TWO_DECIMAL ="^[0-9]+(\\.?[0-9]{3,})+$";
    public static final String ONLY_VALID_OPTIONS ="^[12]?";
    public static String validateData(Validations value) {
        String data;
        String regularExpression = "";
        String firstErrorMessage = value.toString() + " entered contains letters";
        boolean isCustomer = false;
        boolean isProduct = false;
        boolean isQuantity = false;
        boolean isPrice = false;
        boolean isDiscount = false;
        boolean isPostalCode = false;
        boolean isOrder = false;
        boolean isOption = false;
        switch (value) {
            case CUSTOMER:
                regularExpression = ONLY_LETTERS;
                firstErrorMessage = "the customer name entered contains numbers";
                isCustomer = true;
                break;
            case PRODUCT:
                regularExpression = ALL_CHARACTERS;
                isProduct = true;
                break;
            case QUANTITY:
                regularExpression = ONLY_WHOLE_NUMBERS;
                isQuantity = true;
                break;

            case PRICE:
                regularExpression = ONLY_DECIMAL_NUMBERS;

                isPrice = true;
                break;
            case DISCOUNT:
                regularExpression = ONLY_DECIMAL_NUMBERS;
                isDiscount = true;

                break;

            case POSTAL_CODE:
                regularExpression = ONLY_WHOLE_NUMBERS;
                isPostalCode = true;
                break;
            case OPTION:
                regularExpression = ONLY_WHOLE_NUMBERS;
                isOption = true;
                break;
            case ORDER_ID:
                regularExpression = FORMAT_ORDER_ID;
                isOrder = true;
                break;

        }
        while (true) {
            Scanner scanner = new Scanner(System.in);
            data = scanner.nextLine().trim();

            if (data.matches(regularExpression)) {
                if (!(isCustomer || isProduct)) {
                    if (data.startsWith("-")) {
                        System.out.println("Error! The " + value.toString() + " entered is negative\n" +
                                TRY_AGAIN_MESSAGE);
                        continue;
                    }
                    if (isPrice || isDiscount) {
                        if (data.matches(ONLY_TWO_DECIMAL)) {
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
                    if (isQuantity || isPrice || isOption) {
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
                    if (isOption) {
                        if (!data.matches(ONLY_VALID_OPTIONS)) {
                            System.out.println("The option entered does not exist\n" +
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
                if (!isOrder) {
                    if (data.matches(NUMBERS_LETTERS)) {
                        System.out.println("Error! " + firstErrorMessage +
                                "\nplease try again");
                        continue;
                    }
                }
                if (data.matches(ALL_CHARACTERS)) {
                    System.out.println("Error! the " + value.toString() + " contains invalid characters");
                    if (isPrice || isDiscount) {
                        System.out.println("(use '.' up to 2 decimal places)");
                    }
                    if (isOrder) {
                        System.out.println("use the following format =>  US-####-##### ");
                    }
                }

                System.out.println("please try again:");
            }
        }
    }
}
