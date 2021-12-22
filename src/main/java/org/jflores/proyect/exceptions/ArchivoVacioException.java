package org.jflores.proyect.exceptions;

public class ArchivoVacioException extends RuntimeException{
    public ArchivoVacioException(String mensaje){
        System.out.println(mensaje);
    }
}
