package org.jflores.proyect;

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
    static LocalDateTime inicio;
    static LocalDateTime fin;
    private static int cID;
    private static int pID;

    public static void main(String[] args) {
        inicio = LocalDateTime.now();
        System.out.println(inicio);

        leerArchivoCSV("C:\\Users\\JoSe\\Desktop\\Proyecto1\\datosbd.csv");
        new DataBase().csvToMysql();
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
            asignarID();

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
        Product product = new Product();
        Order order = new Order();

        customer.setcName(field[5]);
        customer.setCountry(field[7]);
        customer.setCity(field[8]);
        customer.setState(field[9]);
        customer.setPostalCode(Integer.parseInt(field[10]));
        product.setpName(field[15]);
        product.setCategory(field[13]);
        product.setSub_category(field[14]);
        product.setSales(Double.parseDouble(field[16].replace(',', '.')));
        order.setId(Integer.parseInt(field[0]));
        order.setOrderDate(field[2]);
        order.setQuantity(Integer.parseInt(field[17]));
        order.setProfit(Double.parseDouble(field[20].replace(',', '.')));
        order.setTotal(Double.parseDouble(field[19].replace(',', '.')));
        order.setCustomer(customer);
        order.setProduct(product);


        if (!customerList.contains(customer)) {
            customer.setcustomer_ID(++cID);
            customerList.add(customer);
        }
        if (!productList.contains(product)) {
            product.setProduct_ID(++pID);
            productList.add(product);
        }
        orderList.add(order);
    }


    public static void asignarID() {

        for (Order o : orderList) {
            for (Customer c1 : customerList) {
                if (o.getCustomer().equals(c1)) {
                    o.setCustomer_ID(c1.getcustomer_ID());
                    break;
                }
            }
            for (Product p1 : productList) {
                if (o.getProduct().equals(p1)) {
                    o.setProduct_ID(p1.getProduct_ID());
                    break;
                }
            }
        }


    }

}



