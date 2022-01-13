package org.jflores.project;

import org.jflores.project.exceptions.RecordsNotFoundException;

import java.util.List;

import static org.jflores.project.ValidationHelper.*;

public class Reports {
    ReportsDAO reportsDAO = new ReportsDAO();

    public void generateDailyReport() {
        System.out.println("===== Enter the date for the report   (day/month/year) =====");
        String date = validateDate(scanner.nextLine());
        List<Double> totalSales = findDailyTotalSales(date);
        double dailyTotal = computeTotal(totalSales);
        System.out.println("============== Date: " + date + " ==============");
        System.out.println("Total Sales = " + dailyTotal);
    }

    private double computeTotal(List<Double> totalSales) {
        double total = 0;
        for (Double d : totalSales) {
            total += d;
        }
        return total;
    }

    private List<Double> findDailyTotalSales(String date){
        while (true) {
            try {
               return reportsDAO.findDailyTotalSalesInDb(date);

            } catch (RecordsNotFoundException e) {
                System.out.println(TRY_AGAIN_MESSAGE);
                date = validateDate(scanner.nextLine());
            }
        }
    }
}
