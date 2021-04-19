package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidPeopleNameException extends Exception {
    public InvalidPeopleNameException(){
        super("'name' parameter is not valid. It should be longer than 3 chars.");
    }
}
