package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidCardTypeException extends Exception {
    public InvalidCardTypeException() {
        super("'cardType' parameter is not valid. It should be 'credit' or 'debit'.");
    }
}
