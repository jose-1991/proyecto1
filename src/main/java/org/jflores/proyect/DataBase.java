package org.jflores.proyect;

import java.sql.*;

public class DataBase {
    private Connection getConnection() throws SQLException {
        return ConectionDB.getInstance();
    }

    public void csvToMysql() {
        String sql = "INSERT INTO store.customer(customer_ID, cName)" +
                "VALUES(?,?)";
        try {
            PreparedStatement cStmt = getConnection().prepareStatement(sql);
            getConnection().setAutoCommit(false);
            UserStore.customerList.forEach(c -> {
                try {
                    cStmt.setString(1, c.getcustomer_ID());
                    cStmt.setString(2, c.getcName());

                    cStmt.addBatch();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            cStmt.executeBatch();
            getConnection().commit();
            cStmt.close();

             sql = "INSERT INTO store.address(address_ID, country, state, city, postalCode) " +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement aStmt = getConnection().prepareStatement(sql);
            UserStore.addressList.forEach(a -> {
                try {
                    aStmt.setInt(1, a.getAddress_ID());
                    aStmt.setString(2, a.getCountry());
                    aStmt.setString(3, a.getState());
                    aStmt.setString(4, a.getCity());
                    aStmt.setInt(5, a.getPostalCode());
                    aStmt.addBatch();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            aStmt.executeBatch();
            getConnection().commit();
            aStmt.close();

            sql = "INSERT INTO store.product(product_ID, category, sub_category, pName) " +
                    "VALUES(?,?,?,?)";
            PreparedStatement pStmt = getConnection().prepareStatement(sql);
            UserStore.productList.forEach(p -> {
                try {
                    pStmt.setString(1, p.getProduct_ID());
                    pStmt.setString(2, p.getCategory());
                    pStmt.setString(3, p.getSub_category());
                    pStmt.setString(4, p.getpName());
                    pStmt.addBatch();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            pStmt.executeBatch();
            getConnection().commit();
            pStmt.close();

            sql = "INSERT INTO store.order(order_ID, orderDate, customer_ID, address_ID, product_ID, sales, quantity, discount, total, profit)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement oStmt = getConnection().prepareStatement(sql);
            UserStore.orderList.forEach(o -> {
                try {
                    oStmt.setString(1, o.getOrder_ID());
                    oStmt.setString(2, o.getOrderDate());
                    oStmt.setString(3, o.getCustomer_ID());
                    oStmt.setInt(4, o.getAddress_ID());
                    oStmt.setString(5, o.getProduct_ID());
                    oStmt.setDouble(6, o.getSales());
                    oStmt.setInt(7, o.getQuantity());
                    oStmt.setDouble(8, o.getDiscount());
                    oStmt.setDouble(9, o.getTotal());
                    oStmt.setDouble(10, o.getProfit());
                    oStmt.addBatch();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
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
