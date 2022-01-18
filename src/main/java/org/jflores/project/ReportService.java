package org.jflores.project;

import org.jflores.project.exceptions.RecordsNotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.jflores.project.ValidationHelper.*;

public class ReportService {
    ReportsDAO reportsDAO = new ReportsDAO();

    public void generateDailyReport() {
        System.out.println("===== Enter the date for the report   (dd/mm/yyyy) =====");
        String date = validateDate(scanner.nextLine());
        List<Double> totalSales = findDailyTotalSales(date);

        System.out.println("============== Date: " + date + " ==============");
        double dailyTotal = computeTotal(totalSales);
        System.out.println("Total Sales = " + dailyTotal);
    }

    public void generateTopTenProductPerYear() {
        System.out.println("====== Enter the year =====");
        int year = validateIsPositiveInteger(scanner.nextLine(), MIN_YEAR, getCurrentYear());
        List<String> topTenProducts = findTopTenProducts(year);

        System.out.println("============= Top 10 product in year: " + year + " =============\n");
        topTenProducts.forEach(System.out::println);

    }

    private List<String> findTopTenProducts(int year) {
        while (true) {

            try {
                return reportsDAO.findTopTenProductPerYearInDb(String.valueOf(year));
            } catch (RecordsNotFoundException e) {
                System.out.println(TRY_AGAIN_MESSAGE);
                year = validateIsPositiveInteger(scanner.nextLine(), MIN_YEAR, getCurrentYear());
            }
        }
    }

    private double computeTotal(List<Double> totalSales) {
        double total = 0;
        for (Double d : totalSales) {
            total += d;
        }
        return total;
    }

    private List<Double> findDailyTotalSales(String date) {
        while (true) {

            try {
                return reportsDAO.findDailyTotalSalesInDb(date);

            } catch (RecordsNotFoundException e) {
                System.out.println(TRY_AGAIN_MESSAGE);
                date = validateDate(scanner.nextLine());
            }
        }
    }

    private int getCurrentYear() {
        LocalDate date = LocalDate.now();
        return date.getYear();
    }
}
