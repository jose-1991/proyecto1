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
                throw new RecordsNotFoundException("no record was found on date: " + date);
            }
        } catch (SQLException e) {
            System.out.println("there was an error searching for the total sales");
            e.printStackTrace();
        }
        return totalSales;
    }
}
