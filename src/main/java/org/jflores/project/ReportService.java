package org.jflores.project;

import org.jflores.project.exceptions.RecordsNotFoundException;
import org.jflores.project.models.StateAndQuantity;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public void generateTopStateReportPerProduct() {
        System.out.println("====== Enter Product Name =====");
        String productName = validateIsNotEmpty(scanner.nextLine());
        List<StateAndQuantity> stateAndQuantityList = findTopState(productName);

        System.out.println("======= Top state for product: " + productName + " =======");
        String topState = computeTopState(stateAndQuantityList);
        System.out.println(topState);

    }

    public void generateTopCustomerReportPerState() {
        System.out.println("======== Enter State =======");
        String state = validateOnlyLetters(scanner.nextLine());
        List<String> customerList = findTopCustomer(state);
        System.out.println("======== Top customer for state: "+ state + " ==========");
        String topCustomer = computeTopCustomer(customerList);
        System.out.println(topCustomer);
    }

    private String computeTopCustomer(List<String> customerList) {
        Map<String,Integer> topCustomerMap = new HashMap<>();
        for (String c: customerList){
            if (topCustomerMap.containsKey(c)){
                Integer newValue = topCustomerMap.get(c) + 1;
                topCustomerMap.put(c,newValue);
            }else {
                topCustomerMap.put(c, 1);
            }
        }
        Map<String, Integer> topCustomerMapSorted = mapSortedByValueReversed(topCustomerMap);

        return (String) topCustomerMapSorted.keySet().toArray()[0];
    }

    private Map<String, Integer> mapSortedByValueReversed(Map<String, Integer> map) {
       return map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private List<String> findTopCustomer(String state) {
        while (true) {
            try {
                return reportsDAO.findTopCustomerPerStateInDb(state);
            } catch (RecordsNotFoundException e) {
                System.out.println(TRY_AGAIN_MESSAGE);
                state = validateOnlyLetters(scanner.nextLine());
            }
        }
    }

    private String computeTopState(List<StateAndQuantity> stateAndQuantityList) {
        Map<String, Integer> stateAndQuantityMap = new HashMap<>();
        for (StateAndQuantity s : stateAndQuantityList) {
            if (stateAndQuantityMap.containsKey(s.getState())) {
                Integer currentQuantity = stateAndQuantityMap.get(s.getState());
                s.setQuantity(s.getQuantity() + currentQuantity);
            }
            stateAndQuantityMap.put(s.getState(), s.getQuantity());
        }
        Map<String, Integer> stateAndQuantityMapSorted = mapSortedByValueReversed(stateAndQuantityMap);

        return (String) stateAndQuantityMapSorted.keySet().toArray()[0];
    }

    private List<StateAndQuantity> findTopState(String productName) {
        while (true) {

            try {
                return reportsDAO.findStateAndQuantityPerProductInDb(productName);
            } catch (RecordsNotFoundException e) {
                System.out.println(TRY_AGAIN_MESSAGE);
                productName = validateIsNotEmpty(scanner.nextLine());
            }
        }
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
