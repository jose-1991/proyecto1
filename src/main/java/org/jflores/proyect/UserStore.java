package org.jflores.proyect;

import org.jflores.proyect.modelos.Address;
import org.jflores.proyect.modelos.Customer;
import org.jflores.proyect.modelos.Order;
import org.jflores.proyect.modelos.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
    static LocalDateTime inicio;
    static LocalDateTime fin;


    public static void main(String[] args) {
        inicio = LocalDateTime.now();
        System.out.println(inicio);
        DataBase dataBase = new DataBase();

        leerArchivoCSV("C:\\Users\\JoSe\\Desktop\\Proyecto1\\StoreData.csv");

        fin = LocalDateTime.now();
        System.out.println(fin);


    }

    public static void leerArchivoCSV(String file) {

        final String SEPARATOR = ";";

        BufferedReader bufferLectura = null;
        try {
            if (!file.endsWith(".csv")) {
                throw new Exception();
            }
            bufferLectura = new BufferedReader(new FileReader(file));
            bufferLectura.readLine();
            String line;
            line = bufferLectura.readLine();

            while (line != null) {
                String[] contenido = line.split(SEPARATOR);
                rellenarListas(contenido);
                line = bufferLectura.readLine();

            }

        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado!");
        } catch (Exception e) {
            System.out.println("seleccione un archivo con extension .csv");
        } finally {
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                    System.out.println(" ocurrio un error al leer el archivo");
                }
            }
        }
    }

    public static void rellenarListas(String[] field) {
        Customer customer = new Customer();
        Address address = new Address();
        Product product = new Product();
        Order order = new Order();

        order.setOrder_ID(field[0]);
        order.setOrderDate(field[1]);
        order.setCustomer_ID(field[3]);
        order.setAddress_ID(Integer.parseInt(field[9]));
        order.setProduct_ID(field[11]);
        order.setPrice(Double.parseDouble(field[15].replace(',', '.')));
        order.setQuantity(Integer.parseInt(field[16]));
        order.setDiscount(Double.parseDouble(field[17].replace(',', '.')));
        order.setTotal(Double.parseDouble(field[18].replace(',', '.')));
        order.setProfit(Double.parseDouble(field[19].replace(',', '.')));
        customer.setcustomer_ID(field[3]);
        customer.setcName(field[4]);
        address.setAddress_ID(order.getAddress_ID());
        address.setCountry(field[6]);
        address.setCity(field[7]);
        address.setState(field[8]);
        address.setPostalCode(order.getAddress_ID());
        product.setProduct_ID(field[11]);
        product.setCategory(field[12]);
        product.setSub_category(field[13]);
        product.setpName(field[14]);

        if (!customerList.contains(customer)) {
            customerList.add(customer);
        }
        if (!addressList.contains(address)) {
            addressList.add(address);
        }
        if (!productList.contains(product)) {
            productList.add(product);
        }

        orderList.add(order);
    }


}



