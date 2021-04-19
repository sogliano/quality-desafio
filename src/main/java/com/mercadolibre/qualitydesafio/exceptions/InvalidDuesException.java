package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidDuesException extends Exception{
    public InvalidDuesException(String msg, String type){
        super("'dues' parameter is not valid. It should be " + msg + " on " + type + " cards.");
    }
}
