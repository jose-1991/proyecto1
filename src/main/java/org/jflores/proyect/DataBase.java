package org.jflores.proyect;

import org.jflores.proyect.modelos.*;

import java.sql.*;


public class DataBase {
    private Connection getConnection() throws SQLException {
        return ConectionDB.getInstance();
    }

    public void listToDbTables() {
        for (Tables t : Tables.values()) {
            try {
                PreparedStatement statement = getConnection().prepareStatement(t.toString());
                getConnection().setAutoCommit(false);

                switch (t) {
                    case CUSTOMER:
                        for (Customer c : UserStore.customerList) {

                            statement.setString(1, c.getCustomerId());
                            statement.setString(2, c.getCustomerName());
                            statement.addBatch();

                        }
                        System.out.println("se guardaron registros en  tabla customer con exito!");
                        break;

                    case ADDRESS:
                        for (Address a : UserStore.addressList) {

                            statement.setInt(1, a.getAddressId());
                            statement.setString(2, a.getCountry());
                            statement.setString(3, a.getState());
                            statement.setString(4, a.getCity());
                            statement.setInt(5, a.getPostalCode());
                            statement.addBatch();
                        }
                        System.out.println("se guardaron registros en  tabla address con exito!");
                        break;

                    case PRODUCT:
                        for (Product p : UserStore.productList) {

                            statement.setString(1, p.getProductId());
                            statement.setString(2, p.getCategory());
                            statement.setString(3, p.getSubCategory());
                            statement.setString(4, p.getProductName());
                            statement.addBatch();
                        }
                        System.out.println("se guardaron registros en  tabla product con exito!");
                        break;

                    case ORDER:
                        for (Order o : UserStore.orderList) {

                            statement.setString(1, o.getOrderId());
                            statement.setString(2, o.getOrderDate());
                            statement.setString(3, o.getCustomerId());
                            statement.setInt(4, o.getAddressId());
                            statement.setString(5, o.getProductId());
                            statement.setDouble(6, o.getPrice());
                            statement.setInt(7, o.getQuantity());
                            statement.setDouble(8, o.getDiscount());
                            statement.setDouble(9, o.getTotal());
                            statement.setDouble(10, o.getProfit());
                            statement.addBatch();
                        }
                        System.out.println("se guardaron registros en  tabla order con exito!");
                        break;

                }
                statement.executeBatch();
                getConnection().commit();
                statement.close();


            } catch (SQLException exception) {
                System.out.println("hubo un error al tratar de guardar registros a la Base de Datos");
                exception.printStackTrace();
            }
        }
    }

    public void cleanDbTables() {
        try {
            Statement statement = getConnection().createStatement();

            statement.executeUpdate("DELETE FROM store.order");
            statement.executeUpdate("DELETE FROM store.address");
            statement.executeUpdate("DELETE FROM store.product");
            statement.executeUpdate("DELETE FROM store.customer");
            statement.close();

        } catch (SQLException exception) {
            System.out.println("hubo un error al tratar de eliminar los registros");
            exception.printStackTrace();
        }
    }

}
