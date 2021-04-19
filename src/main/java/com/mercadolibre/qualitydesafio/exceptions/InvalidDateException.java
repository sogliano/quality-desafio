package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super("Date parameters are not valid. Departure date should not be more recent than arrival date.");
    }
}
