package org.jflores.proyect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserStore {
    private Connection getConnection() throws SQLException {
        return ConectionDB.getInstance();
    }

    public  void listar() {


        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM store.order")) {

            while (rs.next()) {
                System.out.println((rs.getInt("id")));
                System.out.println((rs.getInt("customer_ID")));
                System.out.println((rs.getInt("producto_ID")));
                System.out.println((rs.getString("orderDate")));
                System.out.println((rs.getInt("quantity")));
                System.out.println((rs.getDouble("total")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
      // System.out.println(leerArchivoCSV("C:\\Users\\JoSe\\Desktop\\Proyecto1\\datosbd.csv"));
        new UserStore().listar();


    }

    public static List<String> leerArchivoCSV(String file) {

        final String SEPARADOR = ",";
        BufferedReader bufferLectura = null;
        List<String> linea = new ArrayList<>();
        try {
            if (!file.endsWith(".csv")) {
                throw new Exception();
            }
            bufferLectura = new BufferedReader(new FileReader(file));
            String line = bufferLectura.readLine();

            while (line != null) {

                String[] campos = line.split(SEPARADOR);
                linea.add(Arrays.toString(campos) + "\n");
                line = bufferLectura.readLine();

            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado!");
        } catch (Exception ex) {
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
        return linea;
    }
}

