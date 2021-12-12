package org.jflores.proyect;

import org.jflores.proyect.modelos.Address;
import org.jflores.proyect.modelos.Customer;
import org.jflores.proyect.modelos.Order;
import org.jflores.proyect.modelos.Product;

import java.sql.*;

public class DataBase {
    private Connection getConnection() throws SQLException {
        return ConectionDB.getInstance();
    }

    public void listToMysql() {
        String sql = "INSERT INTO store.customer(customer_ID, cName)" +
                "VALUES(?,?)";
        try {

            Statement st = getConnection().createStatement();
            st.executeUpdate("DELETE FROM store.order");
            st.executeUpdate("DELETE FROM store.address");
            st.executeUpdate("DELETE FROM store.product");
            st.executeUpdate("DELETE FROM store.customer");
            st.close();

            PreparedStatement cStmt = getConnection().prepareStatement(sql);
            getConnection().setAutoCommit(false);

            for (Customer c: UserStore.customerList) {

                cStmt.setString(1, c.getcustomer_ID());
                cStmt.setString(2, c.getcName());
                cStmt.addBatch();
            }
            cStmt.executeBatch();
            getConnection().commit();
            cStmt.close();

             sql = "INSERT INTO store.address(address_ID, country, state, city, postalCode) " +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement aStmt = getConnection().prepareStatement(sql);
           for (Address a: UserStore.addressList){

                    aStmt.setInt(1, a.getAddress_ID());
                    aStmt.setString(2, a.getCountry());
                    aStmt.setString(3, a.getState());
                    aStmt.setString(4, a.getCity());
                    aStmt.setInt(5, a.getPostalCode());
                    aStmt.addBatch();
                }
            aStmt.executeBatch();
            getConnection().commit();
            aStmt.close();

            sql = "INSERT INTO store.product(product_ID, category, sub_category, pName) " +
                    "VALUES(?,?,?,?)";
            PreparedStatement pStmt = getConnection().prepareStatement(sql);
            for (Product p: UserStore.productList){

                    pStmt.setString(1, p.getProduct_ID());
                    pStmt.setString(2, p.getCategory());
                    pStmt.setString(3, p.getSub_category());
                    pStmt.setString(4, p.getpName());
                    pStmt.addBatch();
                }
            pStmt.executeBatch();
            getConnection().commit();
            pStmt.close();

            sql = "INSERT INTO store.order(order_ID, orderDate, customer_ID, address_ID, product_ID, price, quantity, discount, total, profit)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement oStmt = getConnection().prepareStatement(sql);
            for (Order o: UserStore.orderList){

                    oStmt.setString(1, o.getOrder_ID());
                    oStmt.setString(2, o.getOrderDate());
                    oStmt.setString(3, o.getCustomer_ID());
                    oStmt.setInt(4, o.getAddress_ID());
                    oStmt.setString(5, o.getProduct_ID());
                    oStmt.setDouble(6, o.getPrice());
                    oStmt.setInt(7, o.getQuantity());
                    oStmt.setDouble(8, o.getDiscount());
                    oStmt.setDouble(9, o.getTotal());
                    oStmt.setDouble(10, o.getProfit());
                    oStmt.addBatch();
                }
            oStmt.executeBatch();
            getConnection().commit();

            System.out.println("guardado con exito!!");
            oStmt.close();
            getConnection().setAutoCommit(true);
            getConnection().close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
