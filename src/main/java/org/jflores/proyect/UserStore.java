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

            if (!file.endsWith(".csv")){
                throw new Exception();
            }

            bufferLectura = new BufferedReader(new FileReader(file));

                   String line = bufferLectura.readLine();


            while (line != null) {

                String[] campos = line.split(SEPARADOR);
                linea.add(Arrays.toString(campos)+"\n");
                line = bufferLectura.readLine();

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
