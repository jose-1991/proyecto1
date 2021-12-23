package org.jflores.proyect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jflores.proyect.exceptions.EmptyFileException;
import org.jflores.proyect.exceptions.DifferentExtensionException;
import org.jflores.proyect.modelos.*;

public class UserStore {

    public static final String FILE_NAME = "C:\\Users\\JoSe\\Desktop\\Proyecto1\\StoreData.csv";
    public static final String SEPARATOR = ";";
    public static final String EXTENSION_CSV = ".csv";
    static List<Customer> customerList = new ArrayList<>();
    static List<Product> productList = new ArrayList<>();
    static List<Order> orderList = new ArrayList<>();
    static List<Address> addressList = new ArrayList<>();
    static int nRow = 1;
    static LocalDateTime star;
    static LocalDateTime end;


    public static void main(String[] args) {
        star = LocalDateTime.now();
        System.out.println(star);
        DataBase dataBase = new DataBase();
        try {
            dataBase.cleanDbTables();
            saveCsvToObjectLists(FILE_NAME);
            dataBase.saveListsToDbTables();
            end = LocalDateTime.now();
            System.out.println(end);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado!");
        }
    }

    public static void saveCsvToObjectLists(String fileName) throws IOException {

        if (!fileName.endsWith(EXTENSION_CSV)) {
            throw new DifferentExtensionException("file with different extension, select a .csv file");
        }
        FileReader file = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(file);
        bufferedReader.readLine();
        String line = bufferedReader.readLine();
        if (line == null) {
            throw new EmptyFileException("The selected file is empty");
        }
        while (line != null) {
            nRow++;
            String[] content = line.split(SEPARATOR);
            if (content.length > 0) {
                fillLists(content);
            } else {
                System.out.println("content in row" + nRow + " is empty");
            }
            line = bufferedReader.readLine();
        }
    }


    public static void fillLists(String[] content) {
        boolean isOrderValid = true;
        boolean isEmptyValue;
        for (String l : content) {
             isEmptyValue = l.isEmpty() || content.length < 20;
            if (isEmptyValue) {
                isOrderValid = false;
                break;
            }
        }
        if (isOrderValid) {
            Customer customer = new Customer();
            Address address = new Address();
            Product product = new Product();
            Order order = new Order();

            order.setOrderId(content[0]);
            order.setOrderDate(content[1]);
            order.setAddressId(Integer.parseInt(content[9]));
            order.setCustomerId(content[3]);
            order.setProductId(content[11]);
            order.setPrice(Double.parseDouble(content[15].replace(',', '.')));
            order.setQuantity(Integer.parseInt(content[16]));
            order.setDiscount(Double.parseDouble(content[17].replace(',', '.')));
            order.setTotal(Double.parseDouble(content[18].replace(',', '.')));
            order.setProfit(Double.parseDouble(content[19].replace(',', '.')));
            customer.setCustomerId(content[3]);
            customer.setCustomerName(content[4]);
            address.setAddressId(order.getAddressId());
            address.setCountry(content[6]);
            address.setCity(content[7]);
            address.setState(content[8]);
            address.setPostalCode(order.getAddressId());
            product.setProductId(content[11]);
            product.setCategory(content[12]);
            product.setSubCategory(content[13]);
            product.setProductName(content[14]);

            if (!customerList.contains(customer)) {
                customerList.add(customer);
            }
            if (!addressList.contains(address)) {
                addressList.add(address);
            }
            if (!productList.contains(product)) {
                productList.add(product);
            }
            if (orderList.contains(order)) {
                System.out.println("orderId:" + order.getOrderId() + "  duplicate, this order will not be registered");
            } else {
                orderList.add(order);
            }
        } else {
            System.out.println("an empty value was found in a row" + nRow + ", this order will not be registered");
        }
    }
}


