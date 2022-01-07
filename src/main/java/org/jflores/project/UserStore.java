package org.jflores.project;

import org.jflores.project.models.Validations;

public class UserStore {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        System.out.println("============================  MENU  =========================\n" +
                "-------- Seleccione una de las siguientes opciones ----------\n\n" +
                " 1) Ingresar una nueva orden\n" +
                " 2) modificar una orden\n");

        int option = Integer.parseInt(ValidationHelper.validateData(Validations.OPTION));
        switch (option) {
            case 1:
                orderService.addNewOrder();
                break;
            case 2:
                orderService.modifyOrder();
                break;
        }
    }


}
