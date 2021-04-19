package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidSeatTypeException extends Exception {
    public InvalidSeatTypeException() {
        super("'seatType' parameter is not valid. It should be 'ECONOMY' or 'BUSINESS'.");
    }
}
