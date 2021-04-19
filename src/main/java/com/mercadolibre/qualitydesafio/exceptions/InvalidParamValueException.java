package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidParamValueException extends Exception {
    public InvalidParamValueException(String key){
        super("Parameter value not valid in key: " + key);
    }
}
