package org.jflores.proyect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class UserStore {
    public static final String SEPARADOR = ",";

    public static void main(String[] args) {

        BufferedReader bufferLectura = null;
        try {

            bufferLectura = new BufferedReader(new FileReader("C:\\Users\\JoSe\\Desktop\\Proyecto1\\datosbd.csv"));
            String linea = bufferLectura.readLine();
            int total = 0;

            while (linea != null) {
//                String[] campos = linea.split(SEPARADOR);
//                System.out.println(linea);
                total += 1;
                linea = bufferLectura.readLine();
            }
            System.out.println(total);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
