package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidDestinationException extends Exception {
    public InvalidDestinationException(){
        super("'destination' parameter is not valid. It doesn't exists.");
    }
}
