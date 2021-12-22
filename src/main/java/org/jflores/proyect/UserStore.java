package org.jflores.proyect;

import org.jflores.proyect.exceptions.ArchivoVacioException;
import org.jflores.proyect.exceptions.DiferenteExtensionException;
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
    static int filaId = 1;
    public static final String FILE ="C:\\Users\\JoSe\\Desktop\\Proyecto1\\StoreData.csv";
    public static final String SEPARATOR = ";";
    public static final String EXTENSION_CSV = ".csv";
    static LocalDateTime inicio;
    static LocalDateTime fin;


    public static void main(String[] args) {
        inicio = LocalDateTime.now();
        System.out.println(inicio);
        DataBase dataBase = new DataBase();
        try {
            dataBase.cleanDbTables();
            csvToObjectLists(FILE);
            dataBase.listToDbTables();

            fin = LocalDateTime.now();
            System.out.println(fin);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado!");
        }
    }

    public static void csvToObjectLists(String file) throws IOException {

        if (!file.endsWith(EXTENSION_CSV)) {
            throw new DiferenteExtensionException("archivo con diferente extension, seleccione un archivo .csv");
        }

        BufferedReader bufferLectura = new BufferedReader(new FileReader(file));
        bufferLectura.readLine();
        String line = bufferLectura.readLine();

        if (line == null) {
            throw new ArchivoVacioException("El archivo seleccionado esta vacio");
        }

        while (line != null) {
            ++filaId;
            String[] contenido = line.split(SEPARATOR);
            if (contenido.length > 0) {
                rellenarListas(contenido);
            } else {
                System.out.println("contenido en fila " + filaId + " esta vacio");
            }
            line = bufferLectura.readLine();


        }
    }

    public static void rellenarListas(String[] field) {
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
            System.out.println("se encontro un valor vacio en fila "+filaId +", la orden no se registrara");
        }
    }
}


