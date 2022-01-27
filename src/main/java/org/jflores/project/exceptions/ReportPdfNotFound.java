package org.jflores.project.exceptions;

public class ReportPdfNotFound extends RuntimeException{
    public ReportPdfNotFound(String message){
        System.out.println(message);
    }
}
