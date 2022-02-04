package org.jflores.project;

import org.jflores.project.models.Order;
import org.jflores.project.service.OrderService;
import org.jflores.project.service.ReportService;

import static org.jflores.project.helper.ValidationHelper.*;


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
                " 5) Generate report of the top ten products per year\n" +
                " 6) Generate  report state that generates more orders per product\n" +
                " 7) Generate report top customer per state");

        int option = validateIsPositiveInteger(scanner.nextLine(), MIN_VALUE_INTEGER, MAX_OPTIONS);
        switch (option) {
            case 1:
                Order newOrder = populateNewOder();
                Order orderCreated = orderService.addNewOrder(newOrder);
                System.out.println("orderCreated: \n" + orderCreated);
                break;
            case 2:
                Order modifiedOrder = orderService.modifyOrder("ABC-2323-2333", 10, 0.5);
                System.out.println("modifiedOrder: \n" + modifiedOrder);
                break;
            case 3:
                Order deletedOrder = orderService.deleteOrder("ABC-2323-2333");
                System.out.println("deletedOrder: \n" + deletedOrder);
                break;
            case 4:
                reportService.generateDailyReport();
                break;
            case 5:
                reportService.generateTopTenProductPerYear();
                break;
            case 6:
                reportService.generateTopStateReportPerProduct();
                break;
            case 7:
                reportService.generateTopCustomerReportPerState();
                break;
        }
    }

    private static Order populateNewOder() {
        Order order = new Order();
        order.setCustomerName("Paul Rodd");
        order.setProductName("staples");
        order.setQuantity(5);
        order.setPrice(20.5);
        order.setDiscount(0.1);
        order.setAddressId(2204);
        return order;
    }
}
