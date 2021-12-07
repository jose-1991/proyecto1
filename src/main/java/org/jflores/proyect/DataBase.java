package org.jflores.proyect;

import java.sql.*;

public class DataBase {
    private Connection getConnection() throws SQLException {
        return ConectionDB.getInstance();
    }

    public void csvToMysql() {
        String sql = "INSERT INTO store.customer(cName, country, state, city, postalCode)" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            getConnection().setAutoCommit(false);
            UserStore.customerList.forEach(c -> {
                try {
                    ps.setString(1, c.getcName());
                    ps.setString(2, c.getCountry());
                    ps.setString(3, c.getState());
                    ps.setString(4, c.getCity());
                    ps.setInt(5, c.getPostalCode());
                    ps.addBatch();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            ps.executeBatch();
            getConnection().commit();
            ps.close();

            sql = "INSERT INTO store.product(pName, category, subCategory, sales) " +
                    "VALUES(?,?,?,?)";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            UserStore.productList.forEach(p -> {
                try {
                    stmt.setString(1, p.getpName());
                    stmt.setString(2, p.getCategory());
                    stmt.setString(3, p.getSub_category());
                    stmt.setDouble(4, p.getSales());
                    stmt.addBatch();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            stmt.executeBatch();
            getConnection().commit();
            stmt.close();

            sql = "INSERT INTO store.order(customer_ID, product_ID, orderDate, quantity, total, profit)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = getConnection().prepareStatement(sql);
            UserStore.orderList.forEach(o -> {
                try {
                    pst.setInt(1, o.getCustomer_ID());
                    pst.setInt(2, o.getProduct_ID());
                    pst.setString(3, o.getOrderDate());
                    pst.setInt(4, o.getQuantity());
                    pst.setDouble(5, o.getTotal());
                    pst.setDouble(6, o.getProfit());
                    pst.addBatch();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            pst.executeBatch();
            getConnection().commit();

            System.out.println("guardado con exito!!");
            pst.close();
            getConnection().setAutoCommit(true);
            getConnection().close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
