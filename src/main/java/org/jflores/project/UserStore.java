package org.jflores.project;

import static org.jflores.project.ValidationHelper.scanner;
import static org.jflores.project.ValidationHelper.validateOption;

public class UserStore {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        ReportService reportService = new ReportService();

        System.out.println("============================  MENU  =========================\n" +
                "-------- Select one of the following options ----------\n\n" +
                " 1) Enter a new order\n" +
                " 2) Modify an order\n" +
                " 3) Delete an order\n" +
                " 4) Generate daily report\n" +
                " 5) Generate report of the top ten products per year");

        int option = validateOption(scanner.nextLine());
        switch (option) {
            case 1:
                orderService.addNewOrder();
                break;
            case 2:
                orderService.modifyOrder();
                break;
            case 3:
                orderService.deleteOrder();
                break;
            case 4:
                reportService.generateDailyReport();
                break;
            case 5:
                reportService.generateTopTenProductPerYear();
                break;
        }
    }
}
