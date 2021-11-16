package org.jflores.proyect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserStore {


    public static void main(String[] args){
        System.out.println(leerArchivoCSV("C:\\Users\\JoSe\\Desktop\\Proyecto1\\datosbd.csv"));


    }

    public static List<String> leerArchivoCSV(String file){

        final String SEPARADOR = ",";
        BufferedReader bufferLectura = null;
        List<String> linea = new ArrayList<>();
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
        }
        catch (IOException e) {
            System.out.println("Archivo no encontrado!");;
        }catch (Exception ex){
            System.out.println("selecione un archivo con extension .csv");
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
        return linea;
    }
}
