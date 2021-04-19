package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidParamKeyException extends Exception {
    public InvalidParamKeyException() {
        super("Parameter key not valid. Should be: 'dateFrom', 'dateTo' or 'destination");
    }
}
