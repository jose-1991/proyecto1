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
                PreparedStatement stmt = getConnection().prepareStatement(t.toString());
                getConnection().setAutoCommit(false);

                switch (t) {
                    case CUSTOMER:
                        for (Customer c : UserStore.customerList) {

                            stmt.setString(1, c.getCustomerId());
                            stmt.setString(2, c.getCustomerName());
                            stmt.addBatch();

                        }
                        System.out.println("se guardaron registros en  tabla customer con exito!");
                        break;

                    case ADDRESS:
                        for (Address a : UserStore.addressList) {

                            stmt.setInt(1, a.getAddressId());
                            stmt.setString(2, a.getCountry());
                            stmt.setString(3, a.getState());
                            stmt.setString(4, a.getCity());
                            stmt.setInt(5, a.getPostalCode());
                            stmt.addBatch();
                        }
                        System.out.println("se guardaron registros en  tabla address con exito!");
                        break;

                    case PRODUCT:
                        for (Product p : UserStore.productList) {

                            stmt.setString(1, p.getProductId());
                            stmt.setString(2, p.getCategory());
                            stmt.setString(3, p.getSubCategory());
                            stmt.setString(4, p.getProductName());
                            stmt.addBatch();
                        }
                        System.out.println("se guardaron registros en  tabla product con exito!");
                        break;

                    case ORDER:
                        for (Order o : UserStore.orderList) {

                            stmt.setString(1, o.getOrderId());
                            stmt.setString(2, o.getOrderDate());
                            stmt.setString(3, o.getCustomerId());
                            stmt.setInt(4, o.getAddressId());
                            stmt.setString(5, o.getProductId());
                            stmt.setDouble(6, o.getPrice());
                            stmt.setInt(7, o.getQuantity());
                            stmt.setDouble(8, o.getDiscount());
                            stmt.setDouble(9, o.getTotal());
                            stmt.setDouble(10, o.getProfit());
                            stmt.addBatch();
                        }
                        System.out.println("se guardaron registros en  tabla order con exito!");
                        break;

                }
                stmt.executeBatch();
                getConnection().commit();
                stmt.close();


            } catch (SQLException exception) {
                System.out.println("hubo un error al tratar de guardar registros a la Base de Datos");
                exception.printStackTrace();
            }
        }
    }

    public void cleanDbTables() {
        try {
            Statement st = getConnection().createStatement();

            st.executeUpdate("DELETE FROM store.order");
            st.executeUpdate("DELETE FROM store.address");
            st.executeUpdate("DELETE FROM store.product");
            st.executeUpdate("DELETE FROM store.customer");
            st.close();

        } catch (SQLException exception) {
            System.out.println("hubo un error al tratar de eliminar los registros");
            exception.printStackTrace();
        }
    }

}
