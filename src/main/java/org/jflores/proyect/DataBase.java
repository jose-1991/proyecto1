package org.jflores.proyect;

import org.jflores.proyect.modelos.*;

import java.sql.*;


public class DataBase {
    private Connection getConnection() throws SQLException {
        return ConectionDB.getInstance();
    }

    public void listToMysql(Table table) {

        try {
            PreparedStatement stmt = getConnection().prepareStatement(table.toString());
            getConnection().setAutoCommit(false);

            switch (table) {
                case CUSTOMER:
                    for (Customer c : UserStore.customerList) {

                        stmt.setString(1, c.getcustomer_ID());
                        stmt.setString(2, c.getcName());
                        stmt.addBatch();

                    }
                    System.out.println("se guardaron registros en  tabla customer con exito!");
                    break;

                case ADDRESS:
                    for (Address a : UserStore.addressList) {

                        stmt.setInt(1, a.getAddress_ID());
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

                        stmt.setString(1, p.getProduct_ID());
                        stmt.setString(2, p.getCategory());
                        stmt.setString(3, p.getSub_category());
                        stmt.setString(4, p.getpName());
                        stmt.addBatch();
                    }
                    System.out.println("se guardaron registros en  tabla product con exito!");
                    break;

                case ORDER:
                    for (Order o : UserStore.orderList) {

                        stmt.setString(1, o.getOrder_ID());
                        stmt.setString(2, o.getOrderDate());
                        stmt.setString(3, o.getCustomer_ID());
                        stmt.setInt(4, o.getAddress_ID());
                        stmt.setString(5, o.getProduct_ID());
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
            int[] counts = stmt.executeBatch();
            getConnection().commit();
            for (int i : counts) {
                if (i == 0) {
                    getConnection().rollback();
                }
            }
            stmt.close();
            getConnection().setAutoCommit(true);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void borrarRegistrosBD() {
        try {
            Statement st = getConnection().createStatement();

            st.executeUpdate("DELETE FROM store.order");
            st.executeUpdate("DELETE FROM store.address");
            st.executeUpdate("DELETE FROM store.product");
            st.executeUpdate("DELETE FROM store.customer");
            st.close();

        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

}
