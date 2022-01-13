package org.jflores.project.exceptions;

public class RecordsNotFoundException extends RuntimeException{
    public RecordsNotFoundException(String message){
        System.out.println(message);
    }
}
