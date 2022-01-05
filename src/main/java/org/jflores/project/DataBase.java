package org.jflores.project;

import org.jflores.project.exceptions.IdValueNotFoundException;
import org.jflores.project.models.*;

import java.sql.*;

public class DataBase {
    private Connection getConnection() throws SQLException {
        return ConectionDB.getInstance();
    }

    public void saveListsToDbTables() {
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
                        System.out.println("records were saved in customer table successfully!");

                        break;
                    case ADDRESS:
                        for (Address a : UserStore.addressList) {

                            statement.setInt(1, a.getAddressId());
                            statement.setString(2, a.getCountry());
                            statement.setString(3, a.getState());
                            statement.setString(4, a.getCity());
                            statement.addBatch();
                        }
                        System.out.println("records were saved in address table successfully!");

                        break;
                    case PRODUCT:
                        for (Product p : UserStore.productList) {

                            statement.setString(1, p.getProductId());
                            statement.setString(2, p.getCategory());
                            statement.setString(3, p.getSubCategory());
                            statement.setString(4, p.getProductName());
                            statement.addBatch();
                        }
                        System.out.println("records were saved in product table successfully!");

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
                        System.out.println("records were saved in order table successfully!");
                        break;
                }
                statement.executeBatch();
                getConnection().commit();
                statement.close();

            } catch (SQLException exception) {
                System.out.println("there was an error trying to save records to the Database");
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
            System.out.println("there was an error trying to delete the records");
            exception.printStackTrace();
        }
    }

    public String findIdValue(String name, Tables table) {
        String valorId = "";
        String columnLabel = "";
        String query = "";
        String errorMessage = "";
        switch (table) {
            case CUSTOMER:
                query = "SELECT customer_ID FROM store.customer WHERE cName = '" + name + "'";
                columnLabel = "customer_ID";
                errorMessage = "An error has occurred!\n" +
                        "customer name: '" + name + "' not found in customer table";
                break;
            case PRODUCT:
                query = "SELECT product_ID FROM store.product WHERE pName = '" + name + "'";
                columnLabel = "product_ID";
                errorMessage = "An error has occurred!\n" +
                        "product name: '" + name + "' not found in product table";
                break;
        }
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                valorId = resultSet.getString(columnLabel);
            }
            if (valorId.isEmpty()) {
                throw new IdValueNotFoundException(errorMessage);

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return valorId;
    }

    public void addNewOrderToDb(Order order) {
        String query = "INSERT INTO store.order(order_ID, orderDate, customer_ID, address_ID, product_ID, price, quantity, discount, total, profit)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, order.getOrderId());
            statement.setString(2, order.getOrderDate());
            statement.setString(3, order.getCustomerId());
            statement.setInt(4, order.getAddressId());
            statement.setString(5, order.getProductId());
            statement.setDouble(6, order.getPrice());
            statement.setInt(7, order.getQuantity());
            statement.setDouble(8, order.getDiscount());
            statement.setDouble(9, order.getTotal());
            statement.setDouble(10, order.getProfit());
            statement.executeUpdate();

            System.out.println("order saved successfully");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public boolean isEmpty() {
        String query = "SELECT count(*) FROM store.order";

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int count = resultSet.getInt("count(*)");
                if (count > 0) {
                    return false;
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;

    }
}
