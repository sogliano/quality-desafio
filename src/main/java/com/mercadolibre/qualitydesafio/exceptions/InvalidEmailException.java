package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String type){
        super("'" + type + "'" + " parameter is not valid. It should contain '@' and '.something'.");
    }
}
