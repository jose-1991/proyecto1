package org.jflores.project;

import org.jflores.project.exceptions.RecordsNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportsDAO {

    private Connection getConnection() throws SQLException {
        return ConnectionDB.getInstance();
    }

    public List<Double> findDailyTotalSalesInDb(String date) {
        double total;
        List<Double> totalSales = new ArrayList<>();
        String query = "SELECT total FROM store.order WHERE orderDate = '" + date + "'";

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                total = resultSet.getDouble("total");
                totalSales.add(total);
            }
            if (totalSales.isEmpty()) {
                throw new RecordsNotFoundException("no records was found on date: " + date);
            }
        } catch (SQLException e) {
            System.out.println("there was an error searching for the total sales");
            e.printStackTrace();
        }
        return totalSales;
    }

    public List<String> findTopTenProductPerYearInDb(String year) {
        List<String> productsPerYear = new ArrayList<>();
        String productName;
        int quantity;
        int id = 0;
        String query = "SELECT p.pName, sum(quantity) as totalQuantity FROM store.order as o INNER JOIN  product AS p on o.product_ID = p.product_ID \n" +
                "WHERE orderDate LIKE '%" + year + "%' GROUP BY p.pName ORDER BY SUM(quantity) DESC LIMIT 0,10";

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                quantity = resultSet.getInt("totalQuantity");
                productName = resultSet.getString("pName");
                productsPerYear.add("Product " + (++id) + ": " + productName + "\tQuantity: " + quantity);
            }
            if (productsPerYear.isEmpty()) {
                throw new RecordsNotFoundException("no records was found on year: " + year);
            }

        } catch (SQLException e) {
            System.out.println("there was an error when trying to find the best-selling products per year");
            e.printStackTrace();
        }
        return productsPerYear;
    }
}
