package org.jflores.project;

import org.jflores.project.models.Order;
import org.jflores.project.models.Validations;

public class UserStore {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        System.out.println("============================  MENU  =========================\n" +
                "-------- Select one of the following options ----------\n\n" +
                " 1) Enter a new order\n" +
                " 2) Modify an order\n" +
                " 3) Delete an order");

        int option = Integer.parseInt(ValidationHelper.validateData(Validations.OPTION));
        switch (option) {
            case 1:
                orderService.addNewOrder();
                break;
            case 2:
                orderService.modifyOrder();
                break;
            case 3:
                orderService.deleteOrder();
        }
    }
}
