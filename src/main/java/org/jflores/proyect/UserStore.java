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
    private static int aID;
    static LocalDateTime inicio;
    static LocalDateTime fin;


    public static void main(String[] args) {
        inicio = LocalDateTime.now();
        System.out.println(inicio);

        leerArchivoCSV("C:\\Users\\JoSe\\Desktop\\Proyecto1\\datosbd.csv");
        new DataBase().csvToMysql();
//        System.out.println(productList);
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

        order.setOrder_ID(field[1]);
        order.setOrderDate(field[2]);
        order.setCustomer_ID(field[4]);

        order.setProduct_ID(field[12]);
        order.setSales(Double.parseDouble(field[16].replace(',', '.')));
        order.setQuantity(Integer.parseInt(field[17]));
        order.setDiscount(Double.parseDouble(field[18].replace(',', '.')));
        order.setTotal(Double.parseDouble(field[19].replace(',', '.')));
        order.setProfit(Double.parseDouble(field[20].replace(',', '.')));
        customer.setcustomer_ID(field[4]);
        customer.setcName(field[5]);

        address.setCountry(field[7]);
        address.setCity(field[8]);
        address.setState(field[9]);
        address.setPostalCode(Integer.parseInt(field[10]));
        product.setProduct_ID(field[12]);
        product.setCategory(field[13]);
        product.setSub_category(field[14]);
        product.setpName(field[15]);

        if (!customerList.contains(customer)){
            customerList.add(customer);
        }
        if (!addressList.contains(address)) {
            address.setAddress_ID(++aID);
            addressList.add(address);
        }
        if (!productList.contains(product)){
            productList.add(product);
        }
        for (Address a: addressList){
            if (a.equals(address)){
                order.setAddress_ID(a.getAddress_ID());
            }
        }

        orderList.add(order);
    }


}



