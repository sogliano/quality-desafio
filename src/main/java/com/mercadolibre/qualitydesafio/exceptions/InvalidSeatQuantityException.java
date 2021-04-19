package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidSeatQuantityException extends Exception {
    public InvalidSeatQuantityException(){
        super("'seats' parameter is not valid. It should be a number.");
    }
}
