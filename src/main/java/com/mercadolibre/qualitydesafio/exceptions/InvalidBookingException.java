package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidBookingException extends Exception {
    public InvalidBookingException(){
        super("The booking sent is not valid.");
    }
}
