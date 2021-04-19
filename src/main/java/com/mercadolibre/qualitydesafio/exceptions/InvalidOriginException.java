package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidOriginException extends Exception {
    public InvalidOriginException(){
        super("'origin' parameter is not valid. It doesn't exists.");
    }
}
