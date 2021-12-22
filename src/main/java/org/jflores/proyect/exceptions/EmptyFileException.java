package org.jflores.proyect.exceptions;

public class EmptyFileException extends RuntimeException{
    public EmptyFileException(String message){
        System.out.println(message);
    }
}
