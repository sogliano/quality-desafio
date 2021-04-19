package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidParamQuantityException extends Exception {
    public InvalidParamQuantityException(){
        super("Parameter quantity not valid. It should be 0 or 3 (Hotels) / 0 or 4 (Flights).");
    }
}
