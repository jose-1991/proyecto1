package org.jflores.project;

import java.util.Scanner;

public class ValidationHelper {
    public static final int MAX_VALUE_POSTAL_CODE = 100000;
    public final static String TRY_AGAIN_MESSAGE = "please try again";
    public static final String ONLY_LETTERS = "^[A-Za-z\\s']+$";

    static Scanner scanner = new Scanner(System.in);

    public static String validateIsNotEmpty(String value) {
        while (true) {
            if (value.trim().isEmpty()) {
                System.out.println("Error! the data entered is empty\n" +
                        TRY_AGAIN_MESSAGE);
                value = scanner.nextLine();
            } else {
                return value;
            }
        }
    }

    public static String validateOnlyLetters(String value) {
        while (true) {
            value = validateIsNotEmpty(value);
            if (value.matches(ONLY_LETTERS)) {
                return value;
            } else {
                System.out.println("Error! the data entered must be only letters\n" +
                        TRY_AGAIN_MESSAGE);
                value = scanner.nextLine();
            }
        }
    }

    public static int validateIsPositiveInteger(String value) {
        int number;
        while (true) {
            try {
                value = validateIsNotEmpty(value);
                number = Integer.parseInt(value);
                if (number > 0 && IsLessThanSixDigits(number)) {
                    return number;
                } else {
                    System.out.println("Error! the number entered must be positive and up to 5 digits\n" +
                            TRY_AGAIN_MESSAGE);
                    value = scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! you must enter only numbers\n" +
                        TRY_AGAIN_MESSAGE);
                value = scanner.nextLine();

            }

        }
    }

    private static boolean IsLessThanSixDigits(int number) {
        return number < MAX_VALUE_POSTAL_CODE;
    }

    public static double validatePositiveDecimal(String value) {
        double number;
        while (true) {
            try {
                value = validateIsNotEmpty(value);
                number = Double.parseDouble(value);
                if (number < 0) {
                    System.out.println("Error! the number entered must be positive\n" +
                            TRY_AGAIN_MESSAGE);
                    value = scanner.nextLine();
                } else {
                    return Math.round(number * 100.0) / 100.0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! the data is invalid (use '.' for decimal)\n" +
                        TRY_AGAIN_MESSAGE);
                value = scanner.nextLine();

            }
        }
    }

    public static double validatePercentage(String value) {
        double number;
        while (true) {
            number = validatePositiveDecimal(value);
            if (number < 1) {
                return number;
            } else {
                System.out.println("Error! the discount is out to range (from 0.0 to 0.9)\n" +
                        TRY_AGAIN_MESSAGE);
                value = scanner.nextLine();
            }
        }
    }
}
