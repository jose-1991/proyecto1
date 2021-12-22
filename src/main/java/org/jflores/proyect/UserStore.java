package org.jflores.proyect;

import org.jflores.proyect.exceptions.EmptyFileException;
import org.jflores.proyect.exceptions.DifferentExtensionException;
import org.jflores.proyect.modelos.*;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserStore {

    static List<Customer> customerList = new ArrayList<>();
    static List<Product> productList = new ArrayList<>();
    static List<Order> orderList = new ArrayList<>();
    static List<Address> addressList = new ArrayList<>();
    static int rowId = 1;
    public static final String FILE ="C:\\Users\\JoSe\\Desktop\\Proyecto1\\StoreData.csv";
    public static final String SEPARATOR = ";";
    public static final String EXTENSION_CSV = ".csv";
    static LocalDateTime star;
    static LocalDateTime end;


    public static void main(String[] args) {
        star = LocalDateTime.now();
        System.out.println(star);
        DataBase dataBase = new DataBase();
        try {
            dataBase.cleanDbTables();
            csvToObjectLists(FILE);
            dataBase.listToDbTables();

            end = LocalDateTime.now();
            System.out.println(end);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado!");
        }
    }

    public static void csvToObjectLists(String file) throws IOException {

        if (!file.endsWith(EXTENSION_CSV)) {
            throw new DifferentExtensionException("archivo con diferente extension, seleccione un archivo .csv");
        }

        BufferedReader bufferRead = new BufferedReader(new FileReader(file));
        bufferRead.readLine();
        String line = bufferRead.readLine();

        if (line == null) {
            throw new EmptyFileException("El archivo seleccionado esta vacio");
        }

        while (line != null) {
            ++rowId;
            String[] content = line.split(SEPARATOR);
            if (content.length > 0) {
                fillLists(content);
            } else {
                System.out.println("contenido en fila " + rowId + " esta vacio");
            }
            line = bufferRead.readLine();


        }
    }

    public static void fillLists(String[] field) {
        boolean orderStatus = true;
        for (String l : field) {
            if (l.isEmpty() || field.length < 20) {
                orderStatus = false;
                break;
            }
        }
        if (orderStatus) {
            Customer customer = new Customer();
            Address address = new Address();
            Product product = new Product();
            Order order = new Order();

            order.setOrderId(field[0]);
            order.setOrderDate(field[1]);
            order.setAddressId(Integer.parseInt(field[9]));
            order.setCustomerId(field[3]);
            order.setProductId(field[11]);
            order.setPrice(Double.parseDouble(field[15].replace(',', '.')));
            order.setQuantity(Integer.parseInt(field[16]));
            order.setDiscount(Double.parseDouble(field[17].replace(',', '.')));
            order.setTotal(Double.parseDouble(field[18].replace(',', '.')));
            order.setProfit(Double.parseDouble(field[19].replace(',', '.')));
            customer.setCustomerId(field[3]);
            customer.setCustomerName(field[4]);
            address.setAddressId(order.getAddressId());
            address.setCountry(field[6]);
            address.setCity(field[7]);
            address.setState(field[8]);
            address.setPostalCode(order.getAddressId());
            product.setProductId(field[11]);
            product.setCategory(field[12]);
            product.setSubCategory(field[13]);
            product.setProductName(field[14]);

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
                System.out.println("orderId:" + order.getOrderId() + "  duplicado, no se registrara esta orden");
            } else {
                orderList.add(order);
            }
        } else {
            System.out.println("se encontro un valor vacio en fila "+ rowId +", la orden no se registrara");
        }
    }
}


